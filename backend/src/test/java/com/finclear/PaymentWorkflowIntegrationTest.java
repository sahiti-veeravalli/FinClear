package com.finclear;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finclear.repository.AuditLogRepository;
import com.finclear.repository.JournalEntryRepository;
import com.finclear.repository.OutboxEventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
class PaymentWorkflowIntegrationTest {
  @Autowired private MockMvc mvc;
  @Autowired private ObjectMapper json;
  @Autowired private JournalEntryRepository journals;
  @Autowired private AuditLogRepository audits;
  @Autowired private OutboxEventRepository outbox;

  @Test
  void paymentIsAuthenticatedIdempotentAndRecordedAcrossFinancialControls() throws Exception {
    String token = login();
    JsonNode account = json.readTree(mvc.perform(get("/api/v1/accounts")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString()).get(0);
    String accountId = account.get("id").asText();
    BigDecimal openingBalance = account.get("balance").decimalValue();
    long journalCount = journals.count();
    long auditCount = audits.count();
    long outboxCount = outbox.count();
    String key = UUID.randomUUID().toString();
    String request = "{\"accountId\":\"" + accountId + "\",\"merchant\":\"Integration supplier\",\"amount\":1250.50,\"currency\":\"INR\"}";

    String first = mvc.perform(post("/api/v1/payments")
        .header("Authorization", "Bearer " + token).header("Idempotency-Key", key)
        .contentType(MediaType.APPLICATION_JSON).content(request))
        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
    String retry = mvc.perform(post("/api/v1/payments")
        .header("Authorization", "Bearer " + token).header("Idempotency-Key", key)
        .contentType(MediaType.APPLICATION_JSON).content(request))
        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

    assertEquals(json.readTree(first).get("id").asText(), json.readTree(retry).get("id").asText());
    JsonNode updatedAccount = json.readTree(mvc.perform(get("/api/v1/accounts")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString()).get(0);
    assertEquals(0, openingBalance.subtract(new BigDecimal("1250.50")).compareTo(updatedAccount.get("balance").decimalValue()));
    assertEquals(journalCount + 1, journals.count());
    assertEquals(auditCount + 1, audits.count());
    assertEquals(outboxCount + 1, outbox.count());
  }

  private String login() throws Exception {
    String response = mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
        .content("{\"email\":\"admin@finclear.local\",\"password\":\"Admin@12345\"}"))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    return json.readTree(response).get("token").asText();
  }
}
