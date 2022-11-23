create role  postgres  login ;
ALTER   ROLE   postgres   WITH   SUPERUSER ;
create sequence contribution_contribution_key_seq;

create sequence orderline_wallet_club_orderline_key_seq;

create sequence pr_email_sended_email_sended_key_seq;

create sequence pr_email_type_email_type_key_seq;

create sequence pr_orderline_club_wallet_orderline_club_wallet_key_seq;

create sequence pr_orderline_giftcard_orderline_giftcard_key_seq;

create sequence pr_orderline_orderline_key_seq;

create sequence pr_partner_partner_key_seq;

create sequence pr_payers_payer_key_seq;

create sequence pr_payers_stripe_payer_strip_id_seq;

create sequence pr_user_user_key_seq1;

create sequence project_price_projectprice_key_seq;

create sequence tst_loaddatetime_test_key_seq;

create sequence user_klaviyo_user_klaviyo_key_seq;

create table if not exists log_job
(
    jobname       text,
    jobtype       text,
    jobfunction   text,
    startdatetime text,
    enddatetime   text,
    job_key       integer generated always as identity
);

create table if not exists product
(
    product_key        integer generated always as identity
    constraint product_pkey
    primary key,
    product_name       varchar,
    description        varchar,
    price              double precision,
    product_stripe_id  varchar,
    product_webflow_id varchar,
    project_key        integer,
    product_type_key   integer,
    created_at         timestamp with time zone,
    updated_at         timestamp with time zone,
    product_id_ext     integer,
    is_active          boolean
);

create table if not exists "user"
(
    user_key                   bigint generated always as identity
    constraint user_pkey
    primary key,
    user_name                  varchar,
    email                      varchar(255)             not null
    constraint unique_user_email
    unique,
    created_at                 timestamp with time zone not null,
    updated_at                 timestamp with time zone,
    website                    varchar(255),
    profile_image              varchar(255),
    profile_image_small        varchar(255),
    user_type                  varchar(255),
    user_id_ext                bigint
    constraint unique_user_id_django
    unique,
    country_key                integer,
    user_stripe_id             varchar,
    is_published               bit,
    user_webflow_id            varchar,
    published_at               timestamp with time zone,
    user_description           varchar,
    list_of_product_webflow_id character varying[],
    user_name_slug             varchar,
    active_membership          varchar,
    password                   varchar,
    user_secret_code varchar(255),
    user_twofactor_enabled boolean
                                             );

alter sequence pr_user_user_key_seq1 owned by "user".user_key;

create table if not exists wallet
(
    wallet_key       bigint generated always as identity
    constraint wallet_key
    primary key,
    balance          double precision,
    created_at       timestamp with time zone,
    updated_at       timestamp with time zone,
    payer_user_key   integer
    constraint wallet_user
    references "user",
    total_orderlines integer,
    owner_user_key   integer
);

create table if not exists auth_group
(
    id   integer      not null
    constraint auth_group_pkey
    primary key,
    name varchar(150) not null
    constraint auth_group_name_key
    unique
    );

create index if not exists auth_group_name_a6ea08ec_like
    on auth_group (name varchar_pattern_ops);

create table if not exists auth_permission
(
    id              integer generated always as identity
    constraint auth_permission_pkey
    primary key,
    name            varchar(255) not null,
    content_type_id integer      not null,
    codename        varchar(100) not null
    );

create table if not exists auth_group_permissions
(
    id            bigint  not null
    constraint auth_group_permissions_pkey
    primary key,
    group_id      integer not null
    constraint auth_group_permissions_group_id_b120cbf9_fk_auth_group_id
    references auth_group
    deferrable initially deferred,
    permission_id integer not null
    constraint auth_group_permissio_permission_id_84c5c92e_fk_auth_perm
    references auth_permission
    deferrable initially deferred,
    constraint auth_group_permissions_group_id_permission_id_0cd325b0_uniq
    unique (group_id, permission_id)
    );

create index if not exists auth_group_permissions_group_id_b120cbf9
    on auth_group_permissions (group_id);

create index if not exists auth_group_permissions_permission_id_84c5c92e
    on auth_group_permissions (permission_id);

create index if not exists auth_permission_content_type_id_2f476e4b
    on auth_permission (content_type_id);

create table if not exists auth_user
(
    id           integer generated always as identity
    constraint auth_user_pkey
    primary key,
    password     varchar(128)             not null,
    last_login   timestamp with time zone,
                               is_superuser boolean                  not null,
                               username     varchar(150)             not null
    constraint auth_user_username_key
    unique,
    first_name   varchar(150)             not null,
    last_name    varchar(150)             not null,
    email        varchar(254)             not null,
    is_staff     boolean                  not null,
    is_active    boolean                  not null,
    date_joined  timestamp with time zone not null
                               );

create index if not exists auth_user_username_6821ab7c_like
    on auth_user (username varchar_pattern_ops);

create table if not exists auth_user_groups
(
    id       bigint generated always as identity
    constraint auth_user_groups_pkey
    primary key,
    user_id  integer not null
    constraint auth_user_groups_user_id_6a12ed8b_fk_auth_user_id
    references auth_user
    deferrable initially deferred,
    group_id integer not null
    constraint auth_user_groups_group_id_97559544_fk_auth_group_id
    references auth_group
    deferrable initially deferred,
    constraint auth_user_groups_user_id_group_id_94350c0c_uniq
    unique (user_id, group_id)
    );

create index if not exists auth_user_groups_group_id_97559544
    on auth_user_groups (group_id);

create index if not exists auth_user_groups_user_id_6a12ed8b
    on auth_user_groups (user_id);

create table if not exists auth_user_user_permissions
(
    id            bigint generated always as identity
    constraint auth_user_user_permissions_pkey
    primary key,
    user_id       integer not null
    constraint auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id
    references auth_user
    deferrable initially deferred,
    permission_id integer not null
    constraint auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm
    references auth_permission
    deferrable initially deferred,
    constraint auth_user_user_permissions_user_id_permission_id_14a6b632_uniq
    unique (user_id, permission_id)
    );

create index if not exists auth_user_user_permissions_permission_id_1fbb5f2c
    on auth_user_user_permissions (permission_id);

create index if not exists auth_user_user_permissions_user_id_a95ead1b
    on auth_user_user_permissions (user_id);

create table if not exists batch
(
    batch_key         integer generated by default as identity
    constraint batch_key
    primary key,
    created_at        timestamp with time zone,
    updated_at        timestamp with time zone,
    text_planned      varchar,
    text_completed    varchar,
    batch_size        integer,
    batch_product_key integer,
    batch_invoice_key integer
);

create table if not exists batch_invoice
(
    batch_invoice_key  integer generated always as identity
    constraint batch_invoice_key
    primary key,
    amount_total       double precision,
    completed_at       timestamp with time zone,
    invoice_nr         varchar,
    invoice_unit_price double precision
);

create table if not exists contributions_website
(
);

create table if not exists country
(
    country_key              integer not null
    constraint pr_countries_pkey
    primary key,
    country_alpha2           varchar,
    country_alpha3           varchar,
    country_name             varchar,
    country_flag_image_small varchar
);

create table if not exists email_type
(
    email_type_key integer generated always as identity
    constraint email_type_pkey
    primary key,
    description    varchar(255) not null,
    text           varchar
    );

alter sequence pr_email_type_email_type_key_seq owned by email_type.email_type_key;

create table if not exists log_zip_images
(
    batch_key       text,
    zipname         text,
    extractdatetime text,
    zip_key         integer generated always as identity
);

create table if not exists lz_b2b_orderlines
(
    "ORDERNUMBER"                                     text,
    "ORDERLINE"                                       text,
    "MAIL"                                            text,
    "NAME"                                            text,
    "PROJECT"                                         text,
    "ORDERDATE"                                       text,
    "QUANTITY"                                        bigint,
    "BATCH ID"                                        bigint,
    "Sort payment"                                    text,
    "PRICE-EXFEE"                                     double precision,
    "FEE-EXBTW"                                       double precision,
    "VAT"                                             double precision,
    "FEE"                                             double precision,
    "TOTAL-EXFEE"                                     double precision,
    "TOTAL"                                           double precision,
    "STATUS"                                          text,
    "LAST UPDATE"                                     text,
    "CURRENT DAT (HIDDEN)"                            bigint,
    "NEED TO SENT AN EMAIL"                           boolean,
    "Merged Doc ID - Sent MAILS ORDER PLACED"         text,
    "Merged Doc URL - Sent MAILS ORDER PLACED"        text,
    "Link to merged Doc - Sent MAILS ORDER PLACED"    text,
    "Document Merge Status - Sent MAILS ORDER PLACED" text,
    "Filter Rows to Merge"                            double precision
);

create table if not exists lz_b2b_orders
(
    "ORDERNUMBER"                                     text,
    "MAIL"                                            text,
    "NAME"                                            text,
    "PROJECT"                                         text,
    "ORDERDATE"                                       text,
    "QUANTITY"                                        bigint,
    "BATCH ID"                                        bigint,
    "Sort payment"                                    text,
    "PRICE-EXFEE"                                     double precision,
    "FEE-EXBTW"                                       double precision,
    "VAT"                                             double precision,
    "FEE"                                             double precision,
    "TOTAL-EXFEE"                                     double precision,
    "TOTAL"                                           double precision,
    "STATUS"                                          text,
    "LAST UPDATE"                                     text,
    "CURRENT DAT (HIDDEN)"                            bigint,
    "NEED TO SENT AN EMAIL"                           boolean,
    "Merged Doc ID - Sent MAILS ORDER PLACED"         text,
    "Merged Doc URL - Sent MAILS ORDER PLACED"        text,
    "Link to merged Doc - Sent MAILS ORDER PLACED"    text,
    "Document Merge Status - Sent MAILS ORDER PLACED" text,
    "Filter Rows to Merge"                            double precision
);

create table if not exists lz_batches_batch
(
);

create table if not exists lz_customers_customer
(
    id            bigint,
    email         text,
    country       text,
    customer_type text,
    name          text,
    created_at    timestamp with time zone,
    updated_at    timestamp with time zone
);

create table if not exists lz_customers_stripecustomer
(
    stripe_customer_id text,
    customer_id        bigint,
    created_at         timestamp with time zone,
    updated_at         timestamp with time zone
);

create table if not exists lz_image_proof
(
    filename     text,
    batch_key    integer,
    filedatetime timestamp,
    latitude     double precision,
    longitude    double precision,
    url          text
);

create table if not exists lz_orders_order
(
    id                       bigint,
    order_date               timestamp with time zone,
    customer_id              bigint,
    stripe_payment_intent_id text,
    order_description        text,
    created_at               timestamp with time zone,
    updated_at               timestamp with time zone,
    amount                   text,
    currency                 text
);

create table if not exists lz_orders_orderitem
(
    id                          bigint,
    notes                       text,
    order_id                    bigint,
    product_id                  bigint,
    stripe_line_item_id         text,
    unit_reference_in_line_item text,
    quantity                    text,
    batch_id                    text,
    is_order_confirmation_sent  boolean,
    created_at                  timestamp with time zone,
    updated_at                  timestamp with time zone,
    amount                      text,
    currency                    text
);

create table if not exists lz_products_product
(
    id                 bigint,
    title              text,
    description        text,
    stripe_product_id  text,
    webflow_product_id text,
    product_type       text,
    created_at         timestamp with time zone,
    updated_at         timestamp with time zone
);

create table if not exists order_type
(
    order_type_key bigint not null
    constraint ordertype_key
    primary key,
    description    varchar,
    order_type     varchar(255),
    is_ext         bit
    );

create table if not exists "order"
(
    order_key               bigint generated always as identity
    constraint order_key
    primary key,
    order_id_ext            integer not null,
    created_at              timestamp with time zone,
    payment_method          "char",
    payer_user_key          bigint
    constraint order_payer
    references "user",
    order_date              timestamp with time zone,
    description             varchar(255),
    order_stripe_payment_id varchar(255),
    transaction_total       double precision,
    order_type_key          integer
    constraint order_order_type
    references order_type,
    transaction_fee         double precision,
    transaction_vat         double precision,
    currency                char(5),
    user_id_ext             integer
    );

create table if not exists orderline_contribution
(
    orderline_key           bigint generated always as identity
    constraint orderline_key
    primary key,
    order_key               bigint
    constraint orderline_order_v2
    references "order",
    order_id_ext            bigint not null,
    orderline_id_ext        varchar(256),
    notes                   text,
    transaction_line_total  double precision,
    product_key             integer,
    product_id_ext          varchar,
    owner_user_key          integer
    constraint orderline_okey
    references "user",
    wallet_key              integer
    constraint orderline_wallet_v2
    references wallet,
    proof_name              varchar,
    proof_date              timestamp with time zone,
    latitude                double precision,
    longitude               double precision,
    proof_small             varchar,
    proof_medium            varchar,
    proof_large             varchar,
    batch_key               integer
    constraint orderline_batch_v2
    references batch,
    proof_uploaded_datetime timestamp with time zone,
    transaction_line_fee    double precision,
    transaction_line_vat    double precision,
    created_at              timestamp with time zone,
    unit_ref_line_item      varchar,
    orderline_stripe_id     varchar,
    is_published            bit,
    update_at               timestamp with time zone,
    published_at            timestamp with time zone,
                                          orderline_webflow_id    varchar
                                          );

create table if not exists orderline_wallet
(
    orderline_key          integer generated always as identity
    constraint orderline_wallet_club_key
    primary key,
    order_key              bigint
    constraint orderline_wallet_club_order_v2
    references "order",
    order_id_ext           bigint                   not null,
    orderline_id_ext       varchar(256),
    notes                  text,
    created_at             timestamp with time zone not null,
    updated_at             timestamp with time zone not null,
                                         transaction_line_total double precision,
                                         wallet_key             bigint                   not null
                                         constraint orderline_wallet_club_wallet_v2
                                         references wallet,
                                         wallet_is_updated      bit,
                                         orderline_stripe_id    varchar,
                                         product_key            integer,
                                         product_id_ext         varchar
                                         );

alter sequence orderline_wallet_club_orderline_key_seq owned by orderline_wallet.orderline_key;

create table if not exists orderline_giftcard
(
    orderline_key           bigint generated always as identity
    constraint orderline_giftcard_key
    primary key,
    order_key               bigint
    constraint orderline_giftcard_order_v2
    references "order",
    order_id_ext            bigint                   not null,
    quantity                integer,
    notes                   text,
    created_at              timestamp with time zone not null,
    updated_at              timestamp with time zone not null,
    transaction_line_total  double precision,
    expirated_at            date,
    token                   varchar,
    orderline_stripe_id     varchar,
    order_stripe_payment_id varchar,
    orderline_id_ext        varchar,
    product_key             integer[],
    product_id_ext          varchar
);

create table if not exists orderline_xxx_dump
(
    orderline_id                bigint not null,
    notes                       text,
    order_id                    bigint,
    product_id                  double precision,
    stripe_line_item_id         text,
    unit_reference_in_line_item text,
    quantity                    text,
    batch_id                    text,
    is_order_confirmation_sent  boolean,
    created_at                  timestamp with time zone,
    updated_at                  timestamp with time zone,
    amount                      text,
    currency                    text,
    checked_date                timestamp with time zone,
    is_ok                       bit,
    orderline_dump_key          bigint generated always as identity,
    payer_user_key              integer,
    user_name                   varchar,
    email                       varchar
);

create table if not exists partner
(
    partner_key integer generated always as identity
    constraint partner_pkey
    primary key,
    name        varchar,
    description varchar,
    created_at  timestamp with time zone,
    updated_at  timestamp with time zone,
    country     char(50),
    address     varchar,
    email       varchar,
    zipcode     varchar
    );

alter sequence pr_partner_partner_key_seq owned by partner.partner_key;

create table if not exists project
(
    project_key          integer generated always as identity
    constraint project_pkey
    primary key,
    description          varchar(255),
    description_long     varchar,
    created_at           timestamp with time zone,
    updated_at           timestamp with time zone,
                                       latitude             double precision,
                                       longitude            double precision,
                                       name                 varchar,
                                       project_image_medium "char",
                                       partner_key          integer
                                       constraint project_partner
                                       references partner,
                                       project_type_key     integer,
                                       batch_size_default   integer
                                       );

alter sequence contribution_contribution_key_seq owned by project.project_key;

create index if not exists fki_project_partner
    on project (partner_key);

create index if not exists fki_project_project_type
    on project (project_type_key);

create table if not exists pr_batch
(
    batch_key         integer generated by default as identity
    constraint batch_pkey
    primary key,
    created_at        timestamp with time zone,
    updated_at        timestamp with time zone,
    text_planned      varchar,
    text_completed    varchar,
    status            varchar,
    batch_size        integer,
    project_key       integer
    constraint batch_project
    references project,
    batch_invoice_key integer
);

create index if not exists fki_batch_contribution
    on pr_batch (project_key);

create table if not exists pr_batch_invoice
(
    batch_invoice_key  integer generated by default as identity
    constraint batch_invoice_pkey
    primary key,
    amount_total       double precision,
    completed_at       timestamp with time zone,
    invoice_nr         varchar,
    invoice_unit_price double precision
);

create table if not exists pr_notification
(
    notification_key               integer generated always as identity,
    email                          varchar,
    name                           varchar,
    active_sumting_club_membership varchar,
    email_preferences              character varying[],
    created_at                     timestamp with time zone,
    updated_at                     timestamp with time zone,
    is_payer                       boolean
);

create table if not exists pr_order_type
(
    order_type_key bigint not null
    constraint order_type_pkey
    primary key,
    description    varchar,
    type           varchar(255),
    is_ext         bit
    );

create table if not exists pr_orderline_xxx_dump
(
    id                          bigint not null,
    notes                       text,
    order_id                    bigint,
    product_id                  double precision,
    stripe_line_item_id         text,
    unit_reference_in_line_item text,
    quantity                    text,
    batch_id                    text,
    is_order_confirmation_sent  boolean,
    created_at                  timestamp with time zone,
    updated_at                  timestamp with time zone,
    amount                      text,
    currency                    text,
    checked_date                timestamp with time zone,
    is_ok                       bit,
    orderline_dump_key          bigint generated always as identity
);

create table if not exists pr_payer
(
    payer_id_ext bigint
    constraint unique_payer_id_django
    unique,
    email        varchar(255)             not null
    constraint unique_email
    unique,
    created_at   timestamp with time zone not null,
    updated_at   timestamp with time zone,
                               country      varchar(255),
    type         varchar(255),
    payer_key    bigint generated always as identity
    constraint payer_pkey
    primary key,
    name         varchar
    );

alter sequence pr_payers_payer_key_seq owned by pr_payer.payer_key;

create table if not exists pr_order
(
    order_key            bigint generated always as identity
    constraint order_pkey
    primary key,
    order_id_ext         integer not null,
    created_at           timestamp with time zone,
    payment_method       "char",
    payer_id_ext         integer,
    payer_key            bigint
    constraint order_payer
    references pr_payer,
    order_date           timestamp with time zone,
    description          varchar(255),
    order_ext_payment_id varchar(255),
    transaction_total    double precision,
    order_type_key       integer
    constraint order_order_type
    references pr_order_type,
    allocator_key        integer,
    transaction_fee      double precision,
    transaction_vat      double precision,
    currency             char(5)
    );

create index if not exists fki_i
    on pr_order (allocator_key);

create index if not exists fki_order_order_type
    on pr_order (order_type_key);

create index if not exists fki_order_payer
    on pr_order (payer_key);

create index if not exists "fki_payed by Payer"
    on pr_order (payer_key);

create table if not exists pr_orderline_giftcard
(
    notes                 text,
    order_id_ext          bigint                   not null,
    orderline_id_ext      varchar(256),
    created_at            timestamp with time zone not null,
    updated_at            timestamp with time zone not null,
                                        orderline_key         bigint generated always as identity
                                        constraint orderline_giftcard_pkey
                                        primary key,
                                        order_key             bigint
                                        constraint orderline_giftcard_order
                                        references pr_order,
                                        amount_line           double precision,
                                        orderline_id_ext_long varchar,
                                        quantity              integer,
                                        expirated_at          date,
                                        token                 varchar,
                                        unit_ref_line_item    varchar
                                        );

alter sequence pr_orderline_giftcard_orderline_giftcard_key_seq owned by pr_orderline_giftcard.orderline_key;

create index if not exists fki_orderline_giftcard_order
    on pr_orderline_giftcard (order_key);

create table if not exists pr_payer_ext
(
    payer_ext_key     bigint generated always as identity
    constraint payer_ext_pkey
    primary key,
    payer_id_ext      bigint,
    payer_id_ext_long varchar,
    created_at        timestamp with time zone,
    updated_at        timestamp with time zone,
    payer_key         bigint
    constraint payer_ext_payer
    references pr_payer
);

alter sequence pr_payers_stripe_payer_strip_id_seq owned by pr_payer_ext.payer_ext_key;

create index if not exists fki_payer_stripe_payer
    on pr_payer_ext (payer_key);

create table if not exists pr_project
(
    project_key          integer generated always as identity
    constraint project_pkey_v2
    primary key,
    description          varchar(255),
    description_long     varchar,
    unit_price           double precision,
    web_flow_id          varchar,
    project_id_ext       integer,
    created_at           timestamp with time zone,
    updated_at           timestamp with time zone,
                                       project_id_ext_long  varchar,
                                       latitude             double precision,
                                       longitude            double precision,
                                       name                 varchar,
                                       project_image_medium "char",
                                       partner_key          integer
                                       constraint project_partner_v2
                                       references partner,
                                       project_type_key     integer,
                                       batch_size_default   integer,
                                       fee                  double precision
                                       );

create table if not exists pr_wallet
(
    wallet_key   bigint generated always as identity
    constraint wallet_pkey
    primary key,
    balance      double precision,
    created_at   timestamp with time zone,
    updated_at   timestamp with time zone,
    payer_key    integer
    constraint wallet_payer
    references pr_payer,
    number_total integer
);

create table if not exists pr_orderline_wallet_club
(
    notes                 text,
    order_id_ext          bigint                   not null,
    orderline_id_ext      varchar(256),
    created_at            timestamp with time zone not null,
    updated_at            timestamp with time zone not null,
                                        wallet_key            bigint                   not null
                                        constraint orderline_wallet_club_wallet
                                        references pr_wallet,
                                        order_key             bigint
                                        constraint orderline_wallet_club_order
                                        references pr_order,
                                        amount_total          double precision,
                                        orderline_id_ext_long varchar,
                                        orderline_key         integer generated always as identity
                                        constraint orderline_wallet_club_pkey
                                        primary key,
                                        wallet_is_updated     bit
                                        );

alter sequence pr_orderline_club_wallet_orderline_club_wallet_key_seq owned by pr_orderline_wallet_club.orderline_key;

create index if not exists fki_orderline_club_wallet_wallet
    on pr_orderline_wallet_club (wallet_key);

create index if not exists fki_orderline_wallet_club_order
    on pr_orderline_wallet_club (order_key);

create table if not exists pr_orderline_contribution
(
    notes                   text,
    order_id_ext            bigint not null,
    orderline_id_ext        varchar(256),
    quantity                integer,
    orderline_key           bigint generated always as identity
    constraint orderline_pkey
    primary key,
    order_key               bigint
    constraint orderline_order
    references pr_order,
    transaction_line_total  double precision,
    project_key             integer,
    project_id_ext          varchar,
    price                   double precision,
    orderline_id_ext_long   varchar,
    orderline_giftcard_key  integer
    constraint orderline_orderline_giftcard
    references pr_orderline_giftcard,
    wallet_key              integer
    constraint orderline_wallet
    references pr_wallet,
    proof_name              varchar,
    proof_date              timestamp with time zone,
    latitude                double precision,
    longitude               double precision,
    proof_small             varchar,
    proof_medium            varchar,
    proof_large             varchar,
    batch_key               integer
    constraint orderline_batch
    references pr_batch,
    proof_uploaded_datetime timestamp with time zone,
    transaction_line_fee    double precision,
    transaction_line_vat    double precision,
    loaded_at               timestamp with time zone,
                                          unit_ref_line_item      varchar,
                                          owner_key               integer
                                          );

alter sequence pr_orderline_orderline_key_seq owned by pr_orderline_contribution.orderline_key;

create table if not exists email_sended
(
    email_sended_key bigint generated always as identity
    constraint email_sended_pkey
    primary key,
    sended_at        timestamp with time zone,
    email_type_key   integer
    constraint email_sended_email_type
    references email_type,
    orderline_key    bigint
    constraint email_sended_orderline
    references pr_orderline_contribution
);

alter sequence pr_email_sended_email_sended_key_seq owned by email_sended.email_sended_key;

create index if not exists fki_email_sended_email_type
    on email_sended (email_type_key);

create index if not exists fki_email_sended_orderline
    on email_sended (orderline_key);

create index if not exists fki_order_line_order
    on pr_orderline_contribution (order_key);

create index if not exists fki_orderline_batch
    on pr_orderline_contribution (batch_key);

create index if not exists fki_orderline_contribution
    on pr_orderline_contribution (project_key);

create index if not exists fki_orderline_orderline_giftcard
    on pr_orderline_contribution (orderline_giftcard_key);

create index if not exists fki_orderline_ukey
    on pr_orderline_contribution (owner_key);

create index if not exists fki_orderline_wallet
    on pr_orderline_contribution (wallet_key);

create index if not exists fki_wallet_payer
    on pr_wallet (payer_key);

create table if not exists product_klaviyo
(
    product_klaviyo_key integer not null
    constraint product_klaviyo_pkey
    primary key,
    product_key         integer,
    project_key         integer,
    product_name        varchar,
    product_price       numeric,
    klaviyo_product_id  integer,
    image_url           varchar,
    product_url         varchar
);

create table if not exists product_type
(
    product_type_key  integer generated by default as identity
    constraint product_type_key
    primary key,
    product_type_name varchar,
    description       varchar,
    frequency         varchar,
    created_at        timestamp with time zone
);

create table if not exists project_price
(
    project_price_key integer generated always as identity
    constraint project_price_key
    primary key,
    project_key       integer,
    unit_price        double precision,
    unit_price_delta  double precision,
    created_at        timestamp with time zone,
    is_current        bit,
    project_price     double precision
);

alter sequence project_price_projectprice_key_seq owned by project_price.project_price_key;

create table if not exists project_type
(
    project_type_key integer generated always as identity
    constraint project_type_key
    primary key,
    description      varchar
);

create table if not exists xxx_loaddatetime
(
    test_key integer generated always as identity
    constraint tst_loaddatetime_pkey
    primary key,
    datetime timestamp
);

alter sequence tst_loaddatetime_test_key_seq owned by xxx_loaddatetime.test_key;

create table if not exists xx_user_klaviyo
(
    user_klaviyo_key               integer generated always as identity
    constraint user_klaviyo_pkey
    primary key,
    user_key                       integer,
    klaviyo_id                     integer,
    user_name                      varchar,
    email                          varchar,
    personal_url                   varchar,
    first_purchase_date            timestamp with time zone,
    last_purchase_date             timestamp with time zone,
    mail_preferences               character varying[],
    interest_buckets               character varying[],
    bought_product_keys            integer[],
    active_sumting_club_membership integer[]
);

alter sequence user_klaviyo_user_klaviyo_key_seq owned by xx_user_klaviyo.user_klaviyo_key;

create or replace view "00_check_cronjob"(jobname, jobtype, jobfunction, startdatetime, enddatetime, job_key) as
SELECT log_job.jobname,
       log_job.jobtype,
       log_job.jobfunction,
       log_job.startdatetime,
       log_job.enddatetime,
       log_job.job_key
FROM log_job
ORDER BY log_job.startdatetime DESC;

create or replace view "01_check_recent_orders"
            (user_name, email, order_key, order_date, transaction_total, product_key, product_name) as
SELECT usr.user_name,
       usr.email,
       ord.order_key,
       ord.order_date,
       ord.transaction_total,
       ol.product_key,
       pro.product_name
FROM "order" ord
         JOIN "user" usr ON usr.user_key = ord.payer_user_key
         JOIN orderline_contribution ol ON ol.order_key = ord.order_key
         JOIN product pro ON pro.product_key = ol.product_key
ORDER BY ord.order_date DESC
    LIMIT 100;

create or replace view "10_supported_products_for_each_user"
            (user_key, user_name, email, purchased_products, first_purchase, last_purchase, supported_projects,
             purchased_products_webflow_id, active_membership)
as
SELECT usr.user_key,
       usr.user_name,
       usr.email,
       tmp1.purchased_products,
       tmp1.first_purchase,
       tmp1.last_purchase,
       tmp1.supported_projects,
       tmp1.purchased_products_webflow_id,
       tmp2.product_name AS active_membership
FROM "user" usr
         LEFT JOIN (SELECT ol.owner_user_key,
                           min(ord.created_at)                        AS first_purchase,
                           max(ord.created_at)                        AS last_purchase,
                           array_agg(DISTINCT prd.product_key)        AS purchased_products,
                           array_agg(DISTINCT prd.project_key)        AS supported_projects,
                           array_agg(DISTINCT prd.product_webflow_id) AS purchased_products_webflow_id
                    FROM "order" ord
                             JOIN orderline_contribution ol ON ol.order_key = ord.order_key
                             JOIN product prd ON prd.product_key = ol.product_key
                    GROUP BY ol.owner_user_key) tmp1 ON tmp1.owner_user_key = usr.user_key
         LEFT JOIN (SELECT wal.owner_user_key,
                           tmp3.wallet_key,
                           tmp3.product_name
                    FROM wallet wal
                             JOIN (SELECT tmp1_1.wallet_key,
                                          tmp2_1.orderline_key,
                                          tmp2_1.product_key,
                                          tmp2_1.created_at,
                                          prd.product_name
                                   FROM (SELECT ol_wal.wallet_key,
                                                max(ol_wal.created_at) AS lastpurchased
                                         FROM orderline_wallet ol_wal
                                         GROUP BY ol_wal.wallet_key) tmp1_1
                                            JOIN (SELECT ol_wal.orderline_key,
                                                         ol_wal.product_key,
                                                         ol_wal.wallet_key,
                                                         ol_wal.created_at
                                                  FROM orderline_wallet ol_wal) tmp2_1
                                                 ON tmp1_1.wallet_key = tmp2_1.wallet_key AND
                                                    tmp1_1.lastpurchased = tmp2_1.created_at
                                            JOIN product prd ON prd.product_key = tmp2_1.product_key) tmp3
                                  ON tmp3.wallet_key = wal.wallet_key) tmp2 ON tmp2.owner_user_key = usr.user_key;

create or replace view "11_all_orders_for_each_user"
            (user_key, user_name, email, created_at, updated_at, website, profile_image, profile_text, user_type,
             user_id_ext, country_key, user_stripe_id, is_published, user_webflow_id, published_at, user_description,
             list_of_product_webflow_id, user_name_slug, active_membership, order_key)
as
SELECT usr.user_key,
       usr.user_name,
       usr.email,
       usr.created_at,
       usr.updated_at,
       usr.website,
       usr.profile_image,
       usr.profile_image_small AS profile_text,
       usr.user_type,
       usr.user_id_ext,
       usr.country_key,
       usr.user_stripe_id,
       usr.is_published,
       usr.user_webflow_id,
       usr.published_at,
       usr.user_description,
       usr.list_of_product_webflow_id,
       usr.user_name_slug,
       usr.active_membership,
       ord.order_key
FROM "user" usr
         JOIN "order" ord ON ord.payer_user_key = usr.user_key;

create or replace view "12_all_orderlines_for_each_order"
            (order_key, order_id_ext, created_at, payment_method, payer_user_key, order_date, description,
             order_stripe_payment_id, transaction_total, order_type_key, transaction_fee, transaction_vat, currency,
             user_id_ext, orderline_key, product_key)
as
SELECT ord.order_key,
       ord.order_id_ext,
       ord.created_at,
       ord.payment_method,
       ord.payer_user_key,
       ord.order_date,
       ord.description,
       ord.order_stripe_payment_id,
       ord.transaction_total,
       ord.order_type_key,
       ord.transaction_fee,
       ord.transaction_vat,
       ord.currency,
       ord.user_id_ext,
       ol.orderline_key,
       ol.product_key
FROM orderline_contribution ol
         JOIN "order" ord ON ol.order_key = ord.order_key;

create or replace procedure pr_00_load_all_django()
    language sql
as
$$CALL public.pr_01_load_payer_django();
CALL public.pr_02_load_payer_ext_django();
CALL public.pr_03_load_project_django();
CALL public.pr_04_load_order_django();
CALL public.pr_05_load_orderlines_contributions_django();
CALL public.pr_06_load_new_wallet_django();
CALL public.pr_07_load_orderlines_wallet_club_django();
CALL public.pr_08_update_wallet_club_with_orderlines_wallet_club_django();
CALL public.pr_09_load_orderlines_giftcard_django();
CALL public.pr_10_load_orderlines_xxx_dump();$$;

create or replace procedure pr_01_load_payer_django()
    language sql
as
$$-- INCREMENTAL

INSERT INTO public.pr_payer(
	payer_id_ext,
	email,
	created_at,
	updated_at,
	country,
	name,
	type)

SELECT
    id,
    lower(lz_cus.email),
    lz_cus.created_at,
    lz_cus.updated_at,
    lz_cus.country,
    lz_cus.name,
    lz_cus.customer_type

FROM public.lz_customers_customer lz_cus
         LEFT JOIN public.pr_payer pay
                   ON lower(lz_cus.email)=lower(pay.email)
WHERE pay.email IS NULL
    $$;

create or replace procedure pr_02_load_payer_ext_django()
    language sql
as
$$-- INCREMENTAL
INSERT INTO public.pr_payer_ext(

	payer_id_ext,
	payer_id_ext_long,
	created_at,
	updated_at,
	payer_key)

SELECT

    customer_id,
    stripe_customer_id ,
    cus.created_at,
    cus.updated_at,
    pay.payer_key
FROM public.lz_customers_stripecustomer cus
         INNER JOIN public.pr_payer pay
                    ON cus.customer_id=pay.payer_id_ext
         LEFT JOIN public.pr_payer_ext pay_ext
                   ON pay_ext.payer_id_ext_long= cus.stripe_customer_id
WHERE pay_ext.payer_id_ext_long IS NULL ;$$;

create or replace procedure pr_03_load_project_django()
    language sql
as
$$ -- INCREMENTAL

 INSERT INTO public.pr_project(
	 project_id_ext,
	 description,
	 description_long,
	 unit_price,
	 created_at,
	 updated_at,
	 web_flow_id,
	 project_id_ext_long

	 )

SELECT
    id,
    replace(lower(title),'-', ' '),
    NULL,

    NULL,
    pr.created_at,
    pr.updated_at,
    webflow_product_id,
    stripe_product_id

FROM public.lz_products_product pr
         LEFT JOIN pr_project project
                   ON replace(lower(pr.title),'-', ' ')=project.description
WHERE project.project_key IS NULL
  AND lower(title) not like '%giftcard%'
  AND lower(title) not Like 'sumting club%';
$$;

create or replace procedure pr_04_load_order_django()
    language sql
as
$$-- INCREMENTAl
INSERT INTO public.pr_order(
	order_id_ext,
	order_date,
	order_type_key,
	payer_id_ext,
	order_ext_payment_id,
	description,
	created_at,

	payer_key,
 	transaction_total,
	currency)


SELECT
    lz_ord.id ,
    lz_ord.order_date,
    1,
    lz_ord.customer_id ,
    lz_ord.stripe_payment_intent_id,
    lz_ord.order_description,
    lz_ord.created_at,
    pay.payer_key,
    cast(lz_ord.amount as float),
    lz_ord.currency
FROM public.lz_orders_order lz_ord
         INNER JOIN  public.lz_customers_customer lz_cus
                     ON lz_cus.id=lz_ord.customer_id
         INNER JOIN public.pr_payer pay
                    ON 	lower(lz_cus.email)=lower(pay.email)
         LEFT JOIN public.pr_order ord
                   ON ord.order_ext_payment_id=lz_ord.stripe_payment_intent_id
WHERE ord.order_ext_payment_id IS NULL;$$;

create or replace procedure pr_05_load_orderlines_contributions_django()
    language sql
as
$$-- INCREMENTAL

INSERT INTO public.pr_orderline_contribution(
	order_key,
	notes,
	order_id_ext,
	orderline_id_ext,
	orderline_id_ext_long,
	unit_ref_line_item,
	quantity,
	price,
	transaction_line_total,
	transaction_line_fee,
	transaction_line_vat,

	project_key,
	project_id_ext,
	loaded_at
 	)


SELECT
    ord.order_key,
    lz_ol.notes,
    lz_ol.order_id as order_id_ext,
    CAST(lz_ol.id as char(256)) as orderline_id_ext,
    lz_ol.stripe_line_item_id as orderline_id_ext_long,
    lz_ol.unit_reference_in_line_item,
    1 as quantity,
    NULL as price,
    cast(lz_ol.amount as float) as transaction_line_total,
    NULL as transaction_line_fee,
    NULL as transaction_line_vat,

    proj.project_key,
    proj.project_id_ext,
    NOW()

FROM public.lz_orders_orderitem lz_ol
         INNER join pr_order ord
                    ON lz_ol.order_id=ord.order_id_ext
         INNER JOIN lz_products_product lz_pr
                    ON lz_pr.id=lz_ol.product_id
         INNER JOIN  pr_project proj
                     ON proj.project_id_ext=lz_ol.product_id
         LEFT JOIN pr_orderline_contribution ol_con
                   ON ol_con.orderline_id_ext= cast(lz_ol.id as char(256))
WHERE ol_con.order_id_ext IS NULL
  AND ord.order_type_key=1
  AND NOT lower(lz_pr.title) like '%giftcard%'
  AND NOT lower(lz_pr.title) LIKE '%club%' $$;

comment on procedure pr_05_load_orderlines_contributions_django() is '''Not all are loaded''. Work to be done is batchnumber';

create or replace procedure pr_06_load_new_wallet_django()
    language sql
as
$$-- INCREMENTAL
INSERT INTO public.pr_wallet(
	balance,
	 created_at,
	 payer_key,
	 number_total)

SELECT 	distinct
    0.0as balance,
    NOW(),
    ord.payer_key,
    0 as number_total
FROM lz_orders_orderitem   ol

         INNER JOIN pr_order ord
                    ON ol.order_id=ord.order_id_ext

         INNER JOIN lz_products_product pr
                    ON pr.id=ol.product_id
         LEFT JOIN pr_wallet wal
                   ON wal.payer_key=ord.payer_key
WHERE ord.order_type_key=1
  AND lower(pr.title) LIKE '%club%'
  AND wal.payer_key is NULL
    $$;

create or replace procedure pr_07_load_orderlines_wallet_club_django()
    language sql
as
$$-- INCREMENTAL
INSERT INTO public.pr_orderline_wallet_club(
	notes,
	orderline_id_ext,
	orderline_id_ext_long,
	created_at,
	updated_at,
	order_key,
	order_id_ext,
	wallet_key,
	amount_total,
	wallet_is_updated)


SELECT
    ol.notes,
    ol.id,
    ol.stripe_line_item_id,
    ol.created_at,
    ol.updated_at,
    ord.order_key,
    ol.order_id,
    wal.wallet_key,
    cast(ol.amount as float) as amount_total,
    '0' as has_wallet_updated
FROM lz_orders_orderitem   ol
         INNER JOIN pr_order ord
                    ON ol.order_id=ord.order_id_ext
         INNER JOIN lz_products_product pr
                    ON pr.id=ol.product_id
         INNER JOIN pr_wallet wal
                    ON wal.payer_key=ord.payer_key
         LEFT JOIN pr_orderline_wallet_club cl
                   ON ol.order_id=cl.order_id_ext
WHERE ord.order_type_key=1
  AND lower(pr.title) LIKE '%club%'
  AND cl.order_id_ext IS NULL

    $$;

create or replace procedure pr_08_update_wallet_club_with_orderlines_wallet_club_django()
    language sql
as
$$UPDATE pr_wallet As wal
SET
	balance=wal.balance+tmp.balance,
	number_total=wal.number_total+tmp.number_total,
	updated_at=NOW()
FROM
	(SELECT
		wallet_key,
		sum(amount_total) as balance,
		count(*) as number_total

	 FROM public.pr_orderline_wallet_club
	 WHERE wallet_is_updated='0'
	 GROUP BY wallet_key) as tmp
WHERE wal.wallet_key=tmp.wallet_key;

UPDATE pr_orderline_wallet_club
SET wallet_is_updated='1'
WHERE wallet_is_updated='0'

    $$;

create or replace procedure pr_09_load_orderlines_giftcard_django()
    language sql
as
$$ -- INCREMENTAL
 INSERT INTO public.pr_orderline_giftcard(
	order_key,
	notes,
	order_id_ext,
	orderline_id_ext,
	orderline_id_ext_long,
	amount_line,
	created_at,
	updated_at,
	expirated_at)


SELECT
    ord.order_key,
    notes,
    order_id,
    ol.id,
    stripe_line_item_id,
    cast(substring( ol.notes from position('€' in ol.notes)+1 for 2) as double precision) as amount_line,
    ol.created_at,
    ol.updated_at,
    TO_Date('9999-12-31','yyyy-mm-dd')

FROM public.lz_orders_orderitem ol

         INNER JOIN lz_products_product pr
                    ON pr.id=ol.product_id
         LEFT JOIN pr_order ord
                   ON ol.order_id=ord.order_id_ext
WHERE ord.order_type_key=1
  AND lower(pr.title) like '%gift%'
  AND ord.order_id_ext IS NULL

    $$;

create or replace procedure pr_10_load_orderlines_xxx_dump()
    language sql
as
$$INSERT INTO public.pr_orderline_xxx_dump(
	id,
	notes,
	order_id,
	product_id,
	stripe_line_item_id,
	unit_reference_in_line_item,
	quantity,
	batch_id,
	is_order_confirmation_sent,
	created_at,
	updated_at,
	amount,
	currency)
SELECT
    ol.id,
    ol.notes,
    ol.order_id,
    ol.product_id,
    ol.stripe_line_item_id,
    ol.unit_reference_in_line_item,
    ol.quantity,
    ol.batch_id,
    ol.is_order_confirmation_sent,
    ol.created_at,
    ol.updated_at,
    ol.amount,
    ol.currency

FROM lz_orders_orderitem   ol
         LEFT JOIN pr_order ord
                   ON ol.order_id=ord.order_id_ext
         LEFT JOIN lz_products_product pr
                   ON pr.id=ol.product_id
         LEFT JOIN public.pr_orderline_xxx_dump dump
                   ON dump.stripe_line_item_id=ol.stripe_line_item_id

WHERE dump.stripe_line_item_id IS NULL
  AND (ord.order_id_ext IS NULL
    OR pr.id IS NULL
    OR ord.payer_key IS NULL
    OR ol.amount IS NULL)
    $$;

create or replace procedure pr_20_load_all_b2b()
    language sql
as
$$truncate table lz_b2b_orders;
truncate table lz_b2b_oderlines;
call pr_21_load_payer_b2b();
call pr_22_load_order_b2b();
call pr_23_load_orderlines_contributions_b2b();$$;

create or replace procedure pr_21_load_payer_b2b()
    language sql
as
$$ INSERT INTO public.pr_payer(
	payer_id_ext,
	email,
	name,
	created_at,
	country,
	type)

SELECT distinct
    NULL::bigint as payer_id_ext,
        lower("MAIL"),
    "NAME",
    cast( "ORDERDATE" as date)+ '00:00:01'::time,
        'NLD' as country,
    'BUSINESS' as type
FROM public.lz_b2b_orders ord
         LEFT JOIN pr_payer pay
                   ON lower(ord."MAIL") = lower(pay.email)
WHERE pay.email IS NULL $$;

create or replace procedure pr_22_load_order_b2b()
    language sql
as
$$ INSERT INTO public.pr_order(
		order_id_ext,
		created_at,
    	payer_key,
		order_date,
		transaction_total,
		order_type_key,
		transaction_fee,
	 	transaction_vat)


SELECT
    CAST("ORDERNUMBER" as int),
    NOW(),
    pay.payer_key,
    cast( "ORDERDATE" as date)+ '00:00:01'::time as order_date,
        "TOTAL",
    3 as order_type,
    "FEE",
    "VAT"
FROM public.lz_b2b_orders ord
         inner join public.pr_payer pay
                    on 	lower(ord."MAIL")=lower(pay.email)
         LEFT JOIN (select order_id_ext
                    from pr_order
                    where order_type_key=3 ) pr_ord
                   on pr_ord.order_id_ext = CAST(ord."ORDERNUMBER" as int)
WHERE pr_ord.order_id_ext IS NULL

    $$;

create or replace procedure pr_23_load_orderlines_contributions_b2b()
    language sql
as
$$ INSERT INTO public.pr_orderline_contribution(
	order_key,
	order_id_ext,
	orderline_id_ext,
    created_at,
	transaction_line_fee,
	transaction_line_vat,
	transaction_line_total,
	project_key	)

SELECT
    ord.order_key,
    CAST (ol."ORDERNUMBER" as int),
    "ORDERLINE",
    cast( "ORDERDATE" as date)+ '00:00:01'::time as created_at,
            "FEE"/"QUANTITY" as transaction_line_fee,
    "VAT"/"QUANTITY" as transaction_line_vat,
    "TOTAL"/"QUANTITY" as transaction_line_total,
    proj.project_key

FROM public.lz_b2b_orderlines ol
         INNER join pr_order ord
                    ON CAST (ol."ORDERNUMBER" as int)=ord.order_id_ext
         INNER JOIN  pr_project proj
                     ON proj.description_long=ol."PROJECT"
         LEFT JOIN ( SELECT distinct order_key
                     from pr_orderline_contribution) as con
                   ON con.order_key=ord.order_key
WHERE ord.order_type_key=3

;$$;

create or replace procedure pr_50_update_orderline_contribution_proof()
    language sql
as
$$UPDATE pr_orderline_contribution as con
SET
proof_name=tmp2.filename,
proof_date=tmp2.filedatetime,
latitude=tmp2.latitude,
longitude=tmp2.longitude,
proof_large=tmp2.url,
proof_uploaded_datetime=NOW(),
batch_key=tmp2.batch_key
-- select con.orderline_key, tmp2.*

--FROM pr_orderline_contribution as con
--INNER JOIN
FROM
	(SELECT con.orderline_key, tmp1.*

	FROM (SELECT orderline_key,project_key,
		row_number() over (partition by project_key  order by orderline_key) as rownumber
		FROM public.pr_orderline_contribution
		 WHERE proof_name is NULL)as con

	LEFT OUTER JOIN
		(SELECT
			filename,
			img.batch_key,
			filedatetime,
			latitude,
			longitude,
			url,
			bat.project_key,
			row_number() over (partition by img.batch_key,bat.project_key  order by img.filename) as rownumber
		FROM public.lz_image_proof img
		INNER JOIN  pr_batch bat
		ON bat.batch_key=img.batch_key) as tmp1
	ON  con.project_key=tmp1.project_key
		AND con.rownumber=tmp1.rownumber
	WHERE NOT tmp1.filename is NULL) as tmp2
	WHERE con.orderline_key=tmp2.orderline_key
;$$;

create or replace procedure s00_load_all_django()
    language sql
as
$$CALL public.s01_load_user_django();
CALL public.s02_load_product_django();
CALL public.s03_load_order_django();
CALL public.s04_load_orderlines_contributions_django();
CALL public.s05_load_wallet_django();
CALL public.s06_load_orderlines_wallet_django();
CALL public.s07_update_wallet_with_orderlines_wallet_django();
CALL public.s08_load_orderlines_giftcard_django();
CALL public.s09_load_orderlines_xxx_dump();
CALL public.s10_update_user_product_webflow_id();
$$;

create or replace procedure s01_load_user_django()
    language sql
as
$$
-- INCREMENTAL

INSERT INTO public.user(
	user_id_ext,
	user_stripe_id,
	email,
	created_at,
	--updated_at,
	country_key,
	user_name,
	user_type)

SELECT
    min(id) AS user_id_ext,
    min(lz_str.stripe_customer_id),
    lower(lz_cus.email) as email,
    min(lz_cus.created_at) as created_at,
    --max(lz_cus.updated_at) as updated_at,
    cou.country_key,
    lz_cus.name,
    lz_cus.customer_type

FROM public.lz_customers_customer lz_cus
         INNER JOIN public.lz_customers_stripecustomer lz_str
                    ON lz_str.customer_id=lz_cus.id
         LEFT JOIN public.country cou
                   ON cou.country_alpha2=lz_cus.country
         LEFT JOIN public.user usr -- only new users
                   ON lower(lz_cus.email)=lower(usr.email)
WHERE usr.email IS NULL
GROUP BY
    lower(lz_cus.email),
    --lz_cus.created_at,
    --lz_cus.updated_at,
    cou.country_key,
    lz_cus.name,
    lz_cus.customer_type;

-- DEFAULT FOR MISSING NAMES

UPDATE public.user usr
set user_name=tmp3.nextname
    FROM
	( -- tmp 3
	SELECT
		user_key,
		user_name,
		concat('anonymous planet lover ' , CAST((rownumber+prev_nr) as text)) as nextname
	FROM
		(-- tmp1
		SELECT
			user_key,
			user_name,
			row_number() over ( order by user_key) as rownumber
		FROM public.user
		WHERE user_name is NULL
		) as tmp1
	CROSS JOIN
		(-- tmp2
		SELECT
			MAX(CAST(substring( user_name , 24) as integer)) as prev_nr
		FROM public.user
		WHERE user_name like 'anonymous planet lover%'
		) as tmp2
		) as tmp3
WHERE usr.user_key=tmp3.user_key;


$$;

create or replace procedure s02_load_product_django()
    language sql
as
$$
 -- INCREMENTAL

INSERT INTO public.product(
	 product_id_ext,
	 description,
	 price,
	 created_at,
	 updated_at,
	 product_webflow_id,
	 product_stripe_id,
	 is_active
	 )

SELECT
    lz_prd.id,
    replace(lower(lz_prd.title),'-', ' '),
    NULL,
    lz_prd.created_at,
    lz_prd.updated_at,
    lz_prd.webflow_product_id,
    lz_prd.stripe_product_id,
    '1'

FROM public.lz_products_product lz_prd
         LEFT JOIN product prd  -- only new projects
                   ON lz_prd.stripe_product_id=prd.product_stripe_id
WHERE prd.product_stripe_id IS NULL;

-- SET OLD PRODUCT PRICE IS_ACTIVE=0
UPDATE public.product prd
SET is_active='0'
    FROM
	(-- tmp2
	SELECT prd.product_key, prd.product_name
	FROM public.product prd
	INNER JOIN
		(-- tmp1
		SELECT max(product_key) as max_key, product_name
		FROM public.product
		GROUP BY product_name
		) as tmp1
	ON 	prd.product_name=tmp1.product_name
	WHERE prd.product_key != max_key
		) as TMP2
WHERE prd.product_key=tmp2.product_key

    $$;

create or replace procedure s03_load_order_django()
    language sql
as
$$
-- INCREMENTAl
--*/
INSERT INTO public.order(
	order_id_ext,
	order_date,
	order_type_key,
	user_id_ext,
	order_stripe_payment_id,
	description,
	created_at,
	payer_user_key,
 	transaction_total,
	currency)

SELECT
    lz_ord.id ,
    lz_ord.order_date,
    1,
    lz_ord.customer_id ,
    lz_ord.stripe_payment_intent_id,
    lz_ord.order_description,
    lz_ord.created_at,
    usr.user_key,
    cast(lz_ord.amount as float),
    lz_ord.currency
FROM public.lz_orders_order lz_ord
         INNER JOIN  public.lz_customers_customer lz_cus
                     ON lz_cus.id=lz_ord.customer_id
         INNER JOIN public.user usr
                    ON 	lower(lz_cus.email)=lower(usr.email)
         LEFT JOIN public.order ord  -- only new orders
                   ON ord.order_stripe_payment_id=lz_ord.stripe_payment_intent_id
WHERE ord.order_stripe_payment_id IS NULL;
$$;

create or replace procedure s04_load_orderlines_contributions_django()
    language sql
as
$$
-- INCREMENTAL

INSERT INTO public.orderline_contribution(
	order_key,
	notes,
	order_id_ext,
	orderline_id_ext,
	orderline_stripe_id,
	unit_ref_line_item,
	--quantity,
	transaction_line_total,
	transaction_line_fee,
	transaction_line_vat,
	product_key,
	product_id_ext,
	created_at,
	owner_user_key

 	)
--*/
SELECT
    ord.order_key,
    lz_ol.notes,
    lz_ol.order_id as order_id_ext,
    CAST(lz_ol.id as char(256)) as orderline_id_ext,
    lz_ol.stripe_line_item_id as orderline_id_ext_long,
    lz_ol.unit_reference_in_line_item,
    --1 as quantity,
    cast(lz_ol.amount as float) as transaction_line_total,
    NULL as transaction_line_fee,
    NULL as transaction_line_vat,
    prd.product_key,
    prd.product_id_ext,
    ord.created_at,
    -- CASE WHEN ord.order_type_key=1 THEN usr.user_key
    --	 ELSE NULL
    --	 END
    usr.user_key
FROM public.lz_orders_orderitem lz_ol
         INNER JOIN public.order ord
                    ON lz_ol.order_id=ord.order_id_ext
         INNER JOIN public.user usr
                    ON usr.user_key=ord.payer_user_key
         INNER JOIN public.lz_products_product lz_pr
                    ON lz_pr.id=lz_ol.product_id
         INNER JOIN  public.product prd
                     ON prd.product_id_ext=lz_ol.product_id
         INNER JOIN public.product_type prd_tp
                    on prd_tp.product_type_key=prd.product_type_key
         LEFT JOIN public.orderline_contribution ol_con
                   ON ol_con.orderline_id_ext= cast(lz_ol.id as char(256))
WHERE ol_con.order_id_ext IS NULL
  AND prd_tp.product_type_key in (1)
  AND prd.is_active='1'


    $$;

create or replace procedure s05_load_wallet_django()
    language sql
as
$$
-- INCREMENTAL
INSERT INTO public.wallet(
	 balance,
	 created_at,
	 payer_user_key,
	 total_orderlines,
	owner_user_key)

SELECT 	distinct
    0.0 as balance,
    NOW(),
    ord.payer_user_key,
    0 as total_orderlines,
    ord.payer_user_key
FROM lz_orders_orderitem   lz_ol
         INNER JOIN public.order ord
                    ON lz_ol.order_id=ord.order_id_ext
         INNER JOIN lz_products_product lz_prd
                    ON lz_prd.id=lz_ol.product_id
         LEFT JOIN wallet wal -- only new wallets
                   ON wal.payer_user_key=ord.payer_user_key
WHERE ord.order_type_key=1
  AND lower(lz_prd.title) LIKE '%club%'
  AND wal.payer_user_key is NULL

    $$;

create or replace procedure s06_load_orderlines_wallet_django()
    language sql
as
$$
-- INCREMENTAL
INSERT INTO public.orderline_wallet(
	notes,
	orderline_id_ext,
	orderline_stripe_id,
	product_key,
	product_id_ext,
	created_at,
	updated_at,
	order_key,
	order_id_ext,
	wallet_key,
	transaction_line_total,
	wallet_is_updated)


SELECT
    lz_ol.notes,
    lz_ol.id,
    lz_ol.stripe_line_item_id,
    prd.product_key,
    prd.product_id_ext,
    lz_ol.created_at,
    lz_ol.updated_at,
    ord.order_key,
    lz_ol.order_id,
    wal.wallet_key,
    cast(lz_ol.amount as float) as amount_total,
    '0' as has_wallet_updated
FROM lz_orders_orderitem   lz_ol
         INNER JOIN public.order ord
                    ON lz_ol.order_id=ord.order_id_ext
         INNER JOIN  public.product prd
                     ON prd.product_id_ext=lz_ol.product_id
         INNER JOIN wallet wal
                    ON wal.payer_user_key=ord.payer_user_key
         LEFT JOIN orderline_wallet ol_cl -- only new orderlines_wallet
                   ON lz_ol.order_id=ol_cl.order_id_ext
WHERE ord.order_type_key=1
  AND prd.product_type_key in (2)
  AND ol_cl.order_id_ext IS NULL
  AND NOT lower(lz_ol.notes) like '%remain%'
  AND prd.is_active='1'

    $$;

create or replace procedure s07_update_wallet_with_orderlines_wallet_django()
    language sql
as
$$

UPDATE wallet As wal
SET
    balance=wal.balance+tmp.balance,
    total_orderlines=wal.total_orderlines+tmp.total_orderlines,
    updated_at=NOW()
    FROM
	(-- tmp
	SELECT
		wallet_key,
		sum(transaction_line_total) as balance,
		count(*) as total_orderlines
	 FROM public.orderline_wallet
	 WHERE wallet_is_updated='0'
	 GROUP BY wallet_key
	 ) as tmp
WHERE wal.wallet_key=tmp.wallet_key;

UPDATE orderline_wallet
SET wallet_is_updated='1'
WHERE wallet_is_updated='0'

    $$;

create or replace procedure s08_load_orderlines_giftcard_django()
    language sql
as
$$
 -- INCREMENTAL
 INSERT INTO public.orderline_giftcard(
	order_key,
	notes,
	order_id_ext,
	orderline_id_ext,
	orderline_stripe_id,
	order_stripe_payment_id,
	transaction_line_total,
	created_at,
	updated_at,
	expirated_at)

SELECT
    ord.order_key,
    lz_ol.notes,
    lz_ol.order_id,
    lz_ol.id,
    lz_ol.stripe_line_item_id,
    ord.order_stripe_payment_id,
    cast(substring( lz_ol.notes from position('€' in lz_ol.notes)+1 for 2) as double precision) as transaction_line_total,
    lz_ol.created_at,
    lz_ol.updated_at,
    TO_Date('9999-12-31','yyyy-mm-dd')

FROM public.lz_orders_orderitem lz_ol
         INNER JOIN public.order ord
                    ON ord.order_id_ext=lz_ol.order_id
         INNER JOIN public.product prd
                    ON prd.product_id_ext=lz_ol.product_id
         LEFT JOIN orderline_giftcard ol -- only new orders
                   ON lz_ol.order_id=ol.order_id_ext
WHERE ord.order_type_key=1
  AND ol.order_id_ext IS NULL
  AND prd.product_type_key in (3)
  AND prd.is_active='1'


    $$;

create or replace procedure s09_load_orderlines_xxx_dump()
    language sql
as
$$
INSERT INTO public.orderline_xxx_dump(
	orderline_id,
	payer_user_key,
	user_name,
	email,
	notes,
	order_id,
	product_id,
	stripe_line_item_id,
	unit_reference_in_line_item,
	quantity,
	batch_id,
	is_order_confirmation_sent,
	created_at,
	updated_at,
	amount,
	currency)

SELECT
    ol.id,
    usr.user_key,
    usr.user_name,
    usr.email,
    ol.notes,
    ol.order_id,
    ol.product_id,
    ol.stripe_line_item_id,
    ol.unit_reference_in_line_item,
    ol.quantity,
    ol.batch_id,
    ol.is_order_confirmation_sent,
    ol.created_at,
    ol.updated_at,
    ol.amount,
    ol.currency

FROM lz_orders_orderitem   ol
         LEFT JOIN public.order ord
                   ON ol.order_id=ord.order_id_ext
         LEFT JOIN public.user usr
                   ON ord.payer_user_key=usr.user_key
         LEFT JOIN public.product prd
                   ON prd.product_id_ext=ol.product_id
         LEFT JOIN public.orderline_xxx_dump dump -- only new dumps
                   ON dump.stripe_line_item_id=ol.stripe_line_item_id

WHERE dump.stripe_line_item_id IS NULL
  AND (ord.order_id_ext IS NULL
    OR prd.product_id_ext IS NULL
    OR ord.payer_user_key IS NULL
    OR ol.amount IS NULL
    OR lower(ol.notes) like '%remain%' )

    $$;

create or replace procedure s10_update_user_product_webflow_id()
    language sql
as
$$
--*/
-- UPDATE NEW USERS WITH WEBFLOW_ID PRODUCTS
--/*
UPDATE public.user as usr
SET list_of_product_webflow_id=tmp3.purchased_products_webflow_id,
    active_membership=tmp3.active_membership,
    updated_at=NOW()
    FROM
	( -- tmp3
--*/
			SELECT
				usr.user_key,
				usr.user_name,
				usr.email,
				tmp1.purchased_products,
				tmp1.first_purchase,
				tmp1.last_purchase,
				tmp1.supported_projects,
				CASE WHEN tmp1.purchased_products_webflow_id IS NULL THEN ('{""}'::character varying[])
					  ELSE tmp1.purchased_products_webflow_id
					  END AS purchased_products_webflow_id ,
				CASE WHEN tmp2.product_name IS NULL THEN ''
					  ELSE tmp2.product_name
					  END as active_membership
			FROM public."user" usr
			LEFT JOIN
				(--tmp 1
				SELECT
					owner_user_key,
					Min(ord.created_at) as first_purchase,
					Max(ord.created_at) as last_purchase,
					array_agg( distinct prd.product_key) as purchased_products,
					array_agg(distinct prd.project_key) as supported_projects,
					array_agg(distinct prd.product_webflow_id) as purchased_products_webflow_id
				FROM public.order ord
				INNER JOIN public.orderline_contribution ol
					ON ol.order_key=ord.order_key
				INNER JOIN public.product prd
					ON prd.product_key=ol.product_key
				GROUP BY owner_user_key
				) as tmp1
			 ON tmp1.owner_user_key=usr.user_key
			--/*
			LEFT JOIN
				(--tmp2
				SELECT
					owner_user_key,
					tmp3.wallet_key,
					tmp3.product_name
				FROM public.wallet wal
				INNER JOIN
						(--tmp3
						SELECT
							tmp1.wallet_key,
							tmp2.orderline_key,
							tmp2.product_key,
							tmp2.created_at,
							prd.product_name
						FROM
							(-- tmp1
							SELECT wallet_key, max(created_at) as lastpurchased
							FROM public.orderline_wallet ol_wal
							GROUP BY wallet_key
							) as tmp1
						INNER JOIN
							(-- tmp2
							SELECT orderline_key,
								   product_key,
								   wallet_key,
								   created_at
							FROM public.orderline_wallet ol_wal
							) as tmp2
						ON tmp1.wallet_key=tmp2.wallet_key
							AND tmp1.lastpurchased=tmp2.created_at
						INNER JOIN product prd
							ON prd.product_key=tmp2.product_key
						) as tmp3
					ON tmp3.wallet_key=wal.wallet_key
					) as tmp2
		ON tmp2.owner_user_key=usr.user_key
			) as tmp3
WHERE tmp3.user_key=usr.user_key
--AND updated_at IS NULL
  AND (usr.active_membership is NULL
   OR usr.list_of_product_webflow_id is NULL)
;

-- UPDATE EXISTING USERS, WHEN NEW PRODUCTS ARE PURCHASED
--/*
UPDATE public.user AS usr
SET
    list_of_product_webflow_id=tmp5.purchased_products_webflow_id,
    active_membership=tmp5.active_membership,
    updated_at=NOW()
--*/
--SELECT usr.*, tmp5.*
    FROM
 	(-- tmp5

		SELECT
				usr.user_key,
				usr.user_name,
				usr.email,
				usr.list_of_product_webflow_id ,
				tmp1.purchased_products,
				tmp1.supported_projects,
			    tmp1.purchased_products_webflow_id,
				tmp4.active_membership
			FROM public."user" usr
			INNER JOIN
				(--tmp 1
				SELECT
					owner_user_key,
					Min(ord.created_at) as first_purchase,
					Max(ord.created_at) as last_purchase,
					array_agg( distinct prd.product_key) as purchased_products,
					array_agg(distinct prd.project_key) as supported_projects,
					array_agg(distinct prd.product_webflow_id) as purchased_products_webflow_id
				FROM public.order ord
				INNER JOIN public.orderline_contribution ol
					ON ol.order_key=ord.order_key
				INNER JOIN public.product prd
					ON prd.product_key=ol.product_key
				GROUP BY owner_user_key
				) as tmp1
			 ON tmp1.owner_user_key=usr.user_key
			--/*
			LEFT JOIN
				(--tmp4
				SELECT
					owner_user_key,
					tmp3.wallet_key,
					tmp3.product_name as active_membership
				FROM public.wallet wal
				INNER JOIN
						(--tmp3
						SELECT
							tmp1.wallet_key,
							tmp2.orderline_key,
							tmp2.product_key,
							tmp2.created_at,
							prd.product_name
						FROM
							(-- tmp1
							SELECT wallet_key, max(created_at) as lastpurchased
							FROM public.orderline_wallet ol_wal
							GROUP BY wallet_key
							) as tmp1
						INNER JOIN
							(-- tmp2
							SELECT orderline_key,
								   product_key,
								   wallet_key,
								   created_at
							FROM public.orderline_wallet ol_wal
							) as tmp2
						ON tmp1.wallet_key=tmp2.wallet_key
							AND tmp1.lastpurchased=tmp2.created_at
						INNER JOIN product prd
							ON prd.product_key=tmp2.product_key
						) as tmp3
					ON tmp3.wallet_key=wal.wallet_key
					) as tmp4
		ON tmp4.owner_user_key=usr.user_key
		) AS tmp5

WHERE usr.user_key=tmp5.user_key

  AND
    (NOT usr.list_of_product_webflow_id = tmp5.purchased_products_webflow_id
   OR NOT usr.active_membership = tmp5.active_membership)

    $$;

create or replace procedure s20_load_all_b2b()
    language sql
as
$$
call s21_load_payer_b2b();
call s22_load_order_b2b();
call s23_load_orderlines_contributions_b2b();
$$;

create or replace procedure s21_load_payer_b2b()
    language sql
as
$$
 INSERT INTO public.user(
	--user_id_ext,
	email,
	user_name,
	created_at,
	country_key,
	user_type)

SELECT distinct
    --NULL::bigint as payer_id_ext,
    lower("MAIL"),
    "NAME",
    cast( "ORDERDATE" as date)+ '00:00:01'::time,
        528 as country_skey, --'NLD' as country
    'BUSINESS' as user_type
FROM public.lz_b2b_orders ord
         LEFT JOIN public.user usr -- only new b2b customers
                   ON lower(ord."MAIL") = lower(usr.email)
WHERE usr.email IS NULL

    $$;

create or replace procedure s22_load_order_b2b()
    language sql
as
$$
 INSERT INTO public.order(
		order_id_ext,
		created_at,
    	payer_user_key,
		order_date,
		transaction_total,
		order_type_key,
		transaction_fee,
	 	transaction_vat,
 		user_id_ext)

SELECT
    CAST("ORDERNUMBER" AS int), -- for B2B order_id_ext is a convention
    NOW(),
    usr.user_key,
    cast( "ORDERDATE" AS date)+ '00:00:01'::time AS order_date,
        "TOTAL" AS transaction_total,
    2 AS order_type ,
    "FEE",
    "VAT",
    NULL AS user_id_ext
FROM public.lz_b2b_orders lz_ord
         INNER JOIN public.user usr
                    ON 	lower(lz_ord."MAIL")=lower(usr.email)
         LEFT JOIN (select order_id_ext -- only new b2b orders
                    from public.order
                    where order_type_key=2 ) ord
                   ON ord.order_id_ext = CAST(lz_ord."ORDERNUMBER" as int)
WHERE ord.order_id_ext IS NULL


    $$;

create or replace procedure s23_load_orderlines_contributions_b2b()
    language sql
as
$$
 INSERT INTO public.orderline_contribution(
	order_key,
	order_id_ext,
	orderline_id_ext,
    loaded_at,
	transaction_line_fee,
	transaction_line_vat,
	transaction_line_total,
	product_key	)

SELECT
    ord.order_key,
    CAST (lz_ol."ORDERNUMBER" as int),
    "ORDERLINE",
    NOW(),
    CAST("FEE"/"QUANTITY" as numeric(20, 3)) as transaction_line_fee,
    CAST("VAT"/"QUANTITY" as numeric(20, 3)) as transaction_line_vat,
    CAST("TOTAL"/"QUANTITY" as numeric(20, 3)) as transaction_line_total,
    prd.product_key

FROM public.lz_b2b_orderlines lz_ol
         INNER join public.order ord
                    ON CAST (lz_ol."ORDERNUMBER" as int)=ord.order_id_ext
         INNER JOIN  project prj
                     ON prj.description_long=lz_ol."PROJECT"
         INNER JOIN product prd
                    ON prd.project_key=prj.project_key
         LEFT JOIN ( SELECT distinct order_key
                     from orderline_contribution) as con -- only new contributions
                   ON con.order_key=ord.order_key
WHERE ord.order_type_key=2

    $$;

create or replace procedure s50_update_orderline_contribution_proof()
    language sql
as
$$
UPDATE orderline_contribution as con
SET
    proof_name=tmp2.filename,
    proof_date=tmp2.filedatetime,
    latitude=tmp2.latitude,
    longitude=tmp2.longitude,
    proof_large=tmp2.url,
    proof_uploaded_datetime=NOW(),
    batch_key=tmp2.batch_key

    FROM
	(-- tmp2
	SELECT con.orderline_key, tmp1.*
	FROM (SELECT orderline_key,project_key,
		row_number() over (partition by project_key  order by orderline_key) as rownumber
		FROM public.orderline_contribution
		WHERE proof_name is NULL)as con

	LEFT OUTER JOIN
		(-- tmp1
			SELECT
				filename,
				img.batch_key,
				filedatetime,
				latitude,
				longitude,
				url,
				bat.project_key,
				row_number() over (partition by img.batch_key,bat.project_key  order by img.filename) as rownumber
			FROM public.lz_image_proof img
			INNER JOIN  batch bat
			ON bat.batch_key=img.batch_key
		) as tmp1
	ON  con.project_key=tmp1.project_key
		AND con.rownumber=tmp1.rownumber
	WHERE NOT tmp1.filename is NULL
	) as tmp2
WHERE con.orderline_key=tmp2.orderline_key
;
$$;

create or replace procedure tst_insert()
    language sql
as
$$

INSERT INTO public.xxx_loaddatetime(
	 datetime)
	VALUES ( (NOW()+ interval '2 hour'));$$;

create or replace procedure xx_gd_show_user(IN in_user_name character varying, IN in_email character varying)
    language plpgsql
as
$$
begin
select * from public.user
where
        user_name like CONCAT('%',in_user_name,'%')
   or email like CONCAT('%',in_email, '%');
--commit;
end;
$$;

create or replace procedure xx_s02_load_project_django()
    language sql
as
$$
 -- INCREMENTAL

 INSERT INTO public.project(
	 project_id_ext,
	 description,
	 description_long,
	 unit_price,
	 created_at,
	 updated_at,
	 web_flow_id,
	 project_stripe_id

	 )

SELECT
    prd.id,
    replace(lower(prd.title),'-', ' '),
    NULL,
    NULL,
    prd.created_at,
    prd.updated_at,
    prd.webflow_product_id,
    prd.stripe_product_id

FROM public.lz_products_product prd
         LEFT JOIN project prj  -- only new projects
                   ON replace(lower(prd.title),'-', ' ')=prj.description
WHERE prj.project_key IS NULL
  AND lower(prd.title) not like '%giftcard%'
  AND lower(prd.title) not Like 'sumting club%';
--$$;

create or replace procedure xx_s30_load_user_klaviyo()
    language sql
as
$$
-- INSERT NEW USERS
INSERT INTO public.user_klaviyo
 	( user_key,
	 user_name,
	 email,
	 bought_product_keys,
	 first_purchase_date,
	 last_purchase_date,
	 active_sumting_club_membership
	)

SELECT
    usr.user_key,
    usr.user_name,
    usr.email,

    tmp1.purchased_products,
    tmp1.first_purchase,
    tmp1.last_purchase,
    --supported_projects,
    tmp2.active_club_memberships
FROM public."user" usr
         LEFT JOIN
     (--tmp 1
         SELECT
             owner_user_key,
             Min(ord.created_at) as first_purchase,
             Max(ord.created_at) as last_purchase,
             array_agg( distinct prd.product_key) as purchased_products,
             array_agg(distinct prd.project_key) as supported_projects
         FROM public.order ord
                  INNER JOIN public.orderline_contribution ol
                             ON ol.order_key=ord.order_key
                  INNER JOIN public.product prd
                             ON prd.product_key=ol.product_key
         GROUP BY owner_user_key
     ) as tmp1
     ON tmp1.owner_user_key=usr.user_key
         LEFT JOIN
     (-- tmp2
         SELECT
             owner_user_key,
             array_agg(distinct product_key) as active_club_memberships -- yes or no distinct
         FROM public.wallet wal
                  INNER JOIN public.orderline_wallet ol_wal
                             ON ol_wal.wallet_key= wal.wallet_key
         WHERE 	DATE_PART('day', NOW() - ol_wal.created_at)<=300 -- 300
         GROUP BY owner_user_key
     ) as tmp2
     ON tmp2.owner_user_key=usr.user_key
         LEFT JOIN public.user_klaviyo kla  -- only new user are inserted
                   ON kla.user_key=usr.user_key
WHERE kla.user_key IS NULL;
--*/

-- UPDATE EXISTING users public.user_klaviyo
--/*
UPDATE public.user_klaviyo kla
SET bought_product_keys= tmp3.purchased_products,
    last_purchase_date= tmp3.last_purchase,
    active_sumting_club_membership=tmp3.active_club_memberships
    FROM
 	(-- tmp 3
		SELECT
			usr.user_key,
			usr.user_name,
			usr.email,

			tmp1.purchased_products,
			tmp1.first_purchase,
			tmp1.last_purchase,
			--supported_projects,
			tmp2.active_club_memberships
		FROM public."user" usr
		LEFT JOIN
			(--tmp 1
			SELECT
				owner_user_key,
				Min(ord.created_at) as first_purchase,
				Max(ord.created_at) as last_purchase,
				array_agg( distinct prd.product_key) as purchased_products,
				array_agg(distinct prd.project_key) as supported_projects
			FROM public.order ord
			INNER JOIN public.orderline_contribution ol
				ON ol.order_key=ord.order_key
			INNER JOIN public.product prd
				ON prd.product_key=ol.product_key
			GROUP BY owner_user_key
			) as tmp1
		  ON tmp1.owner_user_key=usr.user_key
		LEFT JOIN
			(-- tmp2
			SELECT
				owner_user_key,
				array_agg(distinct product_key) as active_club_memberships
			FROM public.wallet wal
			INNER JOIN public.orderline_wallet ol_wal
				ON ol_wal.wallet_key= wal.wallet_key
			WHERE 	DATE_PART('day', NOW() - ol_wal.created_at)<=300 -- 300
			GROUP BY owner_user_key
			) as tmp2
		ON tmp2.owner_user_key=usr.user_key
		) AS tmp3
where kla.user_key=tmp3.user_key
  AND (NOT kla.bought_product_keys = tmp3.purchased_products
   OR NOT kla.last_purchase_date =tmp3.last_purchase
   OR NOT kla.active_sumting_club_membership = tmp3.active_club_memberships)

--*/


    $$;

