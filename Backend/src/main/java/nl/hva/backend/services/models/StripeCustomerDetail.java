package nl.hva.backend.services.models;

import java.time.LocalDateTime;

public class StripeCustomerDetail {
    long userId;

    String customerId;

    String subscriptionId;

    LocalDateTime subscriptionEndDate;

    LocalDateTime subscriptionStartDate;

    String subscriptionPlan;
}
