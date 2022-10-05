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

create table if not exists log_job
(
    jobname       text,
    jobtype       text,
    jobfunction   text,
    startdatetime text,
    enddatetime   text,
    job_key       integer generated always as identity
);

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

create table if not exists pr_allocator
(
    allocator_key bigint not null
        constraint allocator_pkey
            primary key,
    name          varchar(255),
    email         varchar(255),
    is_ext        bit,
    created_at    timestamp with time zone,
    country       varchar(255)
);

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

create table if not exists pr_email_type
(
    email_type_key integer generated always as identity
        constraint email_type_pkey
            primary key,
    description    varchar(255) not null,
    text           varchar
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
    id                          bigint,
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
    is_ok                       bit
);

create table if not exists pr_partner
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

create table if not exists pr_payer
(
    payer_id_ext bigint
        constraint unique_payer_id_django
            unique,
    email        varchar(255)             not null,
    created_at   timestamp with time zone not null,
    updated_at   timestamp with time zone,
    country      varchar(255),
    type         varchar(255),
    payer_key    bigint generated always as identity
        constraint payer_pkey
            primary key,
    name         varchar
);

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
    allocator_key        integer
        constraint order_allocator
            references pr_allocator,
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

create index if not exists fki_payer_stripe_payer
    on pr_payer_ext (payer_key);

create table if not exists pr_project_type
(
    project_type_key integer generated always as identity
        constraint project_type_pkey
            primary key,
    description      varchar
);

create table if not exists pr_project
(
    project_key          integer generated always as identity
        constraint project_pkey
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
        constraint project_partner
            references pr_partner,
    project_type_key     integer
        constraint project_project_type
            references pr_project_type,
    batch_size           integer,
    fee                  double precision
);

create index if not exists fki_project_partner
    on pr_project (partner_key);

create index if not exists fki_project_project_type
    on pr_project (project_type_key);

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
            references pr_project,
    batch_invoice_key integer
);

create index if not exists fki_batch_contribution
    on pr_batch (project_key);

create table if not exists pr_project_price
(
    project_price    integer generated always as identity
        constraint project_price_pkey
            primary key,
    project_key      integer
        constraint project_price_project
            references pr_project,
    unit_price       double precision,
    unit_price_delta double precision,
    created_at       timestamp with time zone,
    is_current       bit
);

create index if not exists fki_contribution_price_contribution
    on pr_project_price (project_key);

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
    project_key             integer
        constraint orderline_project
            references pr_project,
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
    unit_ref_line_item      varchar
);

create table if not exists pr_email_sended
(
    email_sended_key bigint generated always as identity
        constraint email_sended_pkey
            primary key,
    sended_at        timestamp with time zone,
    email_type_key   integer
        constraint email_sended_email_type
            references pr_email_type,
    orderline_key    bigint
        constraint email_sended_orderline
            references pr_orderline_contribution
);

create index if not exists fki_email_sended_email_type
    on pr_email_sended (email_type_key);

create index if not exists fki_email_sended_orderline
    on pr_email_sended (orderline_key);

create index if not exists fki_order_line_order
    on pr_orderline_contribution (order_key);

create index if not exists fki_orderline_batch
    on pr_orderline_contribution (batch_key);

create index if not exists fki_orderline_contribution
    on pr_orderline_contribution (project_key);

create index if not exists fki_orderline_orderline_giftcard
    on pr_orderline_contribution (orderline_giftcard_key);

create index if not exists fki_orderline_wallet
    on pr_orderline_contribution (wallet_key);

create index if not exists fki_wallet_payer
    on pr_wallet (payer_key);

create table if not exists xx_orderline_contribution_bckup
(
    notes                   text,
    order_id_ext            bigint not null,
    orderline_id_ext        varchar(256),
    quantity                integer,
    orderline_key           bigint,
    order_key               bigint,
    transaction_line_total  double precision,
    project_key             integer,
    project_id_ext          varchar,
    price                   double precision,
    orderline_id_ext_long   varchar,
    orderline_giftcard_key  integer,
    wallet_key              integer,
    proof_name              varchar,
    proof_date              timestamp with time zone,
    latitude                double precision,
    longitude               double precision,
    proof_small             varchar,
    proof_medium            varchar,
    proof_large             varchar,
    batch_key               integer,
    proof_uploaded_datetime timestamp with time zone,
    transaction_line_fee    double precision,
    transaction_line_vat    double precision,
    created_at              timestamp with time zone,
    unit_ref_line_item      varchar
);


