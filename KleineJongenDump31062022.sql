PGDMP                         z           postgres    14.4    14.4 �    P           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            Q           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            R           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            S           1262    14023    postgres    DATABASE     ]   CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';
    DROP DATABASE postgres;
                postgres    false            T           0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                   postgres    false    3923                        2615    24681 
   heroku_ext    SCHEMA        CREATE SCHEMA heroku_ext;
    DROP SCHEMA heroku_ext;
                postgres    false                       1255    24682    pr_00_load_all_django() 	   PROCEDURE     ;  CREATE PROCEDURE public.pr_00_load_all_django()
    LANGUAGE sql
    AS $$CALL public.pr_01_load_payer_django();
CALL public.pr_02_load_payer_ext_django();
CALL public.pr_03_load_project_django();
CALL public.pr_04_load_order_django();
CALL public.pr_05_load_orderlines_contributions_django();
CALL public.pr_06_load_new_wallet_django();
CALL public.pr_07_load_orderlines_wallet_club_django();
CALL public.pr_08_update_wallet_club_with_orderlines_wallet_club_django();
CALL public.pr_09_load_orderlines_giftcard_django();
CALL public.pr_10_load_orderlines_xxx_dump();$$;
 /   DROP PROCEDURE public.pr_00_load_all_django();
       public          postgres    false                       1255    24683    pr_01_load_payer_django() 	   PROCEDURE     �  CREATE PROCEDURE public.pr_01_load_payer_django()
    LANGUAGE sql
    AS $$-- INCREMENTAL

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
 1   DROP PROCEDURE public.pr_01_load_payer_django();
       public          postgres    false                       1255    24684    pr_02_load_payer_ext_django() 	   PROCEDURE     *  CREATE PROCEDURE public.pr_02_load_payer_ext_django()
    LANGUAGE sql
    AS $$-- INCREMENTAL
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
 5   DROP PROCEDURE public.pr_02_load_payer_ext_django();
       public          postgres    false                       1255    24685    pr_03_load_project_django() 	   PROCEDURE     �  CREATE PROCEDURE public.pr_03_load_project_django()
    LANGUAGE sql
    AS $$ -- INCREMENTAL 
 
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
 3   DROP PROCEDURE public.pr_03_load_project_django();
       public          postgres    false                       1255    24686    pr_04_load_order_django() 	   PROCEDURE     =  CREATE PROCEDURE public.pr_04_load_order_django()
    LANGUAGE sql
    AS $$-- INCREMENTAl
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
 1   DROP PROCEDURE public.pr_04_load_order_django();
       public          postgres    false                       1255    24687 ,   pr_05_load_orderlines_contributions_django() 	   PROCEDURE       CREATE PROCEDURE public.pr_05_load_orderlines_contributions_django()
    LANGUAGE sql
    AS $$-- INCREMENTAL

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
 D   DROP PROCEDURE public.pr_05_load_orderlines_contributions_django();
       public          postgres    false            U           0    0 6   PROCEDURE pr_05_load_orderlines_contributions_django()    COMMENT     �   COMMENT ON PROCEDURE public.pr_05_load_orderlines_contributions_django() IS '''Not all are loaded''. Work to be done is batchnumber';
          public          postgres    false    283                       1255    24688    pr_06_load_new_wallet_django() 	   PROCEDURE     +  CREATE PROCEDURE public.pr_06_load_new_wallet_django()
    LANGUAGE sql
    AS $$-- INCREMENTAL
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
 6   DROP PROCEDURE public.pr_06_load_new_wallet_django();
       public          postgres    false                       1255    24689 *   pr_07_load_orderlines_wallet_club_django() 	   PROCEDURE     x  CREATE PROCEDURE public.pr_07_load_orderlines_wallet_club_django()
    LANGUAGE sql
    AS $$-- INCREMENTAL
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
 B   DROP PROCEDURE public.pr_07_load_orderlines_wallet_club_django();
       public          postgres    false                       1255    24690 =   pr_08_update_wallet_club_with_orderlines_wallet_club_django() 	   PROCEDURE     1  CREATE PROCEDURE public.pr_08_update_wallet_club_with_orderlines_wallet_club_django()
    LANGUAGE sql
    AS $$UPDATE pr_wallet As wal
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
 U   DROP PROCEDURE public.pr_08_update_wallet_club_with_orderlines_wallet_club_django();
       public          postgres    false                       1255    24691 '   pr_09_load_orderlines_giftcard_django() 	   PROCEDURE       CREATE PROCEDURE public.pr_09_load_orderlines_giftcard_django()
    LANGUAGE sql
    AS $$ -- INCREMENTAL
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
 ?   DROP PROCEDURE public.pr_09_load_orderlines_giftcard_django();
       public          postgres    false                        1255    24692     pr_10_load_orderlines_xxx_dump() 	   PROCEDURE     �  CREATE PROCEDURE public.pr_10_load_orderlines_xxx_dump()
    LANGUAGE sql
    AS $$INSERT INTO public.pr_orderline_xxx_dump(
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
 8   DROP PROCEDURE public.pr_10_load_orderlines_xxx_dump();
       public          postgres    false            !           1255    24693    pr_20_load_all_b2b() 	   PROCEDURE     �   CREATE PROCEDURE public.pr_20_load_all_b2b()
    LANGUAGE sql
    AS $$truncate table lz_b2b_orders;
truncate table lz_b2b_oderlines;
call pr_21_load_payer_b2b();
call pr_22_load_order_b2b();
call pr_23_load_orderlines_contributions_b2b();$$;
 ,   DROP PROCEDURE public.pr_20_load_all_b2b();
       public          postgres    false            "           1255    24694    pr_21_load_payer_b2b() 	   PROCEDURE     �  CREATE PROCEDURE public.pr_21_load_payer_b2b()
    LANGUAGE sql
    AS $$ INSERT INTO public.pr_payer(
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
 .   DROP PROCEDURE public.pr_21_load_payer_b2b();
       public          postgres    false            #           1255    24695    pr_22_load_order_b2b() 	   PROCEDURE     �  CREATE PROCEDURE public.pr_22_load_order_b2b()
    LANGUAGE sql
    AS $$ INSERT INTO public.pr_order(
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
 .   DROP PROCEDURE public.pr_22_load_order_b2b();
       public          postgres    false            $           1255    24696 )   pr_23_load_orderlines_contributions_b2b() 	   PROCEDURE     �  CREATE PROCEDURE public.pr_23_load_orderlines_contributions_b2b()
    LANGUAGE sql
    AS $$ INSERT INTO public.pr_orderline_contribution(
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
 A   DROP PROCEDURE public.pr_23_load_orderlines_contributions_b2b();
       public          postgres    false            %           1255    24697 +   pr_50_update_orderline_contribution_proof() 	   PROCEDURE     �  CREATE PROCEDURE public.pr_50_update_orderline_contribution_proof()
    LANGUAGE sql
    AS $$UPDATE pr_orderline_contribution as con
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
 C   DROP PROCEDURE public.pr_50_update_orderline_contribution_proof();
       public          postgres    false            �            1259    24698 
   auth_group    TABLE     f   CREATE TABLE public.auth_group (
    id integer NOT NULL,
    name character varying(150) NOT NULL
);
    DROP TABLE public.auth_group;
       public         heap    postgres    false            �            1259    24701    auth_group_permissions    TABLE     �   CREATE TABLE public.auth_group_permissions (
    id bigint NOT NULL,
    group_id integer NOT NULL,
    permission_id integer NOT NULL
);
 *   DROP TABLE public.auth_group_permissions;
       public         heap    postgres    false            �            1259    24704    auth_permission    TABLE     �   CREATE TABLE public.auth_permission (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    content_type_id integer NOT NULL,
    codename character varying(100) NOT NULL
);
 #   DROP TABLE public.auth_permission;
       public         heap    postgres    false            �            1259    24707    auth_permission_id_seq    SEQUENCE     �   ALTER TABLE public.auth_permission ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.auth_permission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    212            �            1259    24708 	   auth_user    TABLE     �  CREATE TABLE public.auth_user (
    id integer NOT NULL,
    password character varying(128) NOT NULL,
    last_login timestamp with time zone,
    is_superuser boolean NOT NULL,
    username character varying(150) NOT NULL,
    first_name character varying(150) NOT NULL,
    last_name character varying(150) NOT NULL,
    email character varying(254) NOT NULL,
    is_staff boolean NOT NULL,
    is_active boolean NOT NULL,
    date_joined timestamp with time zone NOT NULL
);
    DROP TABLE public.auth_user;
       public         heap    postgres    false            �            1259    24713    auth_user_groups    TABLE     ~   CREATE TABLE public.auth_user_groups (
    id bigint NOT NULL,
    user_id integer NOT NULL,
    group_id integer NOT NULL
);
 $   DROP TABLE public.auth_user_groups;
       public         heap    postgres    false            �            1259    24716    auth_user_groups_id_seq    SEQUENCE     �   ALTER TABLE public.auth_user_groups ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.auth_user_groups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    215            �            1259    24717    auth_user_id_seq    SEQUENCE     �   ALTER TABLE public.auth_user ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.auth_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    214            �            1259    24718    auth_user_user_permissions    TABLE     �   CREATE TABLE public.auth_user_user_permissions (
    id bigint NOT NULL,
    user_id integer NOT NULL,
    permission_id integer NOT NULL
);
 .   DROP TABLE public.auth_user_user_permissions;
       public         heap    postgres    false            �            1259    24721 !   auth_user_user_permissions_id_seq    SEQUENCE     �   ALTER TABLE public.auth_user_user_permissions ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.auth_user_user_permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    218            �            1259    24722 
   pr_project    TABLE     W  CREATE TABLE public.pr_project (
    project_key integer NOT NULL,
    description character varying(255),
    description_long character varying,
    unit_price double precision,
    web_flow_id character varying,
    project_id_ext integer,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    project_id_ext_long character varying,
    latitude double precision,
    longitude double precision,
    name character varying,
    project_image_medium "char",
    partner_key integer,
    project_type_key integer,
    batch_size integer,
    fee double precision
);
    DROP TABLE public.pr_project;
       public         heap    postgres    false            �            1259    24727 !   contribution_contribution_key_seq    SEQUENCE     �   ALTER TABLE public.pr_project ALTER COLUMN project_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.contribution_contribution_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    220            �            1259    24728    log_job    TABLE     �   CREATE TABLE public.log_job (
    jobname text,
    jobtype text,
    jobfunction text,
    startdatetime text,
    enddatetime text,
    job_key integer NOT NULL
);
    DROP TABLE public.log_job;
       public         heap    postgres    false            �            1259    24733    log_job_job_key_seq    SEQUENCE     �   ALTER TABLE public.log_job ALTER COLUMN job_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.log_job_job_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    222            �            1259    24734    log_zip_images    TABLE     �   CREATE TABLE public.log_zip_images (
    batch_key text,
    zipname text,
    extractdatetime text,
    zip_key integer NOT NULL
);
 "   DROP TABLE public.log_zip_images;
       public         heap    postgres    false            �            1259    24739    log_zip_images_zip_key_seq    SEQUENCE     �   ALTER TABLE public.log_zip_images ALTER COLUMN zip_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.log_zip_images_zip_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    224            �            1259    24740    lz_b2b_orderlines    TABLE     )  CREATE TABLE public.lz_b2b_orderlines (
    "ORDERNUMBER" text,
    "ORDERLINE" text,
    "MAIL" text,
    "NAME" text,
    "PROJECT" text,
    "ORDERDATE" text,
    "QUANTITY" bigint,
    "BATCH ID" bigint,
    "Sort payment" text,
    "PRICE-EXFEE" double precision,
    "FEE-EXBTW" double precision,
    "VAT" double precision,
    "FEE" double precision,
    "TOTAL-EXFEE" double precision,
    "TOTAL" double precision,
    "STATUS" text,
    "LAST UPDATE" text,
    "CURRENT DAT (HIDDEN)" bigint,
    "NEED TO SENT AN EMAIL" boolean,
    "Merged Doc ID - Sent MAILS ORDER PLACED" text,
    "Merged Doc URL - Sent MAILS ORDER PLACED" text,
    "Link to merged Doc - Sent MAILS ORDER PLACED" text,
    "Document Merge Status - Sent MAILS ORDER PLACED" text,
    "Filter Rows to Merge" double precision
);
 %   DROP TABLE public.lz_b2b_orderlines;
       public         heap    postgres    false            �            1259    24745    lz_b2b_orders    TABLE       CREATE TABLE public.lz_b2b_orders (
    "ORDERNUMBER" text,
    "MAIL" text,
    "NAME" text,
    "PROJECT" text,
    "ORDERDATE" text,
    "QUANTITY" bigint,
    "BATCH ID" bigint,
    "Sort payment" text,
    "PRICE-EXFEE" double precision,
    "FEE-EXBTW" double precision,
    "VAT" double precision,
    "FEE" double precision,
    "TOTAL-EXFEE" double precision,
    "TOTAL" double precision,
    "STATUS" text,
    "LAST UPDATE" text,
    "CURRENT DAT (HIDDEN)" bigint,
    "NEED TO SENT AN EMAIL" boolean,
    "Merged Doc ID - Sent MAILS ORDER PLACED" text,
    "Merged Doc URL - Sent MAILS ORDER PLACED" text,
    "Link to merged Doc - Sent MAILS ORDER PLACED" text,
    "Document Merge Status - Sent MAILS ORDER PLACED" text,
    "Filter Rows to Merge" double precision
);
 !   DROP TABLE public.lz_b2b_orders;
       public         heap    postgres    false            �            1259    24750    lz_batches_batch    TABLE     *   CREATE TABLE public.lz_batches_batch (
);
 $   DROP TABLE public.lz_batches_batch;
       public         heap    postgres    false            �            1259    24753    lz_customers_customer    TABLE     �   CREATE TABLE public.lz_customers_customer (
    id bigint,
    email text,
    country text,
    customer_type text,
    name text,
    created_at timestamp with time zone,
    updated_at timestamp with time zone
);
 )   DROP TABLE public.lz_customers_customer;
       public         heap    postgres    false            �            1259    24758    lz_customers_stripecustomer    TABLE     �   CREATE TABLE public.lz_customers_stripecustomer (
    stripe_customer_id text,
    customer_id bigint,
    created_at timestamp with time zone,
    updated_at timestamp with time zone
);
 /   DROP TABLE public.lz_customers_stripecustomer;
       public         heap    postgres    false            �            1259    24763    lz_image_proof    TABLE     �   CREATE TABLE public.lz_image_proof (
    filename text,
    batch_key integer,
    filedatetime timestamp without time zone,
    latitude double precision,
    longitude double precision,
    url text
);
 "   DROP TABLE public.lz_image_proof;
       public         heap    postgres    false            �            1259    24768    lz_orders_order    TABLE     -  CREATE TABLE public.lz_orders_order (
    id bigint,
    order_date timestamp with time zone,
    customer_id bigint,
    stripe_payment_intent_id text,
    order_description text,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    amount text,
    currency text
);
 #   DROP TABLE public.lz_orders_order;
       public         heap    postgres    false            �            1259    24773    lz_orders_orderitem    TABLE       CREATE TABLE public.lz_orders_orderitem (
    id bigint,
    notes text,
    order_id bigint,
    product_id bigint,
    stripe_line_item_id text,
    unit_reference_in_line_item text,
    quantity text,
    batch_id text,
    is_order_confirmation_sent boolean,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    amount text,
    currency text
);
 '   DROP TABLE public.lz_orders_orderitem;
       public         heap    postgres    false            �            1259    24778    lz_products_product    TABLE       CREATE TABLE public.lz_products_product (
    id bigint,
    title text,
    description text,
    stripe_product_id text,
    webflow_product_id text,
    product_type text,
    created_at timestamp with time zone,
    updated_at timestamp with time zone
);
 '   DROP TABLE public.lz_products_product;
       public         heap    postgres    false            �            1259    24783    pr_allocator    TABLE     �   CREATE TABLE public.pr_allocator (
    allocator_key bigint NOT NULL,
    name character varying(255),
    email character varying(255),
    is_ext bit(1),
    created_at timestamp with time zone,
    country character varying(255)
);
     DROP TABLE public.pr_allocator;
       public         heap    postgres    false            �            1259    24788    pr_batch    TABLE     K  CREATE TABLE public.pr_batch (
    batch_key integer NOT NULL,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    text_planned character varying,
    text_completed character varying,
    status character varying,
    batch_size integer,
    project_key integer,
    batch_invoice_key integer
);
    DROP TABLE public.pr_batch;
       public         heap    postgres    false            �            1259    24793    pr_batch_batch_key_seq    SEQUENCE     �   ALTER TABLE public.pr_batch ALTER COLUMN batch_key ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.pr_batch_batch_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    236            �            1259    24794    pr_batch_invoice    TABLE     �   CREATE TABLE public.pr_batch_invoice (
    batch_invoice_key integer NOT NULL,
    amount_total double precision,
    completed_at timestamp with time zone,
    invoice_nr character varying,
    invoice_unit_price double precision
);
 $   DROP TABLE public.pr_batch_invoice;
       public         heap    postgres    false            �            1259    24799 &   pr_batch_invoice_batch_invoice_key_seq    SEQUENCE     �   ALTER TABLE public.pr_batch_invoice ALTER COLUMN batch_invoice_key ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.pr_batch_invoice_batch_invoice_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    238            �            1259    24800    pr_project_price    TABLE     �   CREATE TABLE public.pr_project_price (
    project_price integer NOT NULL,
    project_key integer,
    unit_price double precision,
    unit_price_delta double precision,
    created_at timestamp with time zone,
    is_current bit(1)
);
 $   DROP TABLE public.pr_project_price;
       public         heap    postgres    false            �            1259    24803 ,   pr_contribution_price_contribution_price_seq    SEQUENCE     �   ALTER TABLE public.pr_project_price ALTER COLUMN project_price ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pr_contribution_price_contribution_price_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    240            �            1259    24804    pr_email_sended    TABLE     �   CREATE TABLE public.pr_email_sended (
    email_sended_key bigint NOT NULL,
    sended_at timestamp with time zone,
    email_type_key integer,
    orderline_key bigint
);
 #   DROP TABLE public.pr_email_sended;
       public         heap    postgres    false            �            1259    24807 $   pr_email_sended_email_sended_key_seq    SEQUENCE     �   ALTER TABLE public.pr_email_sended ALTER COLUMN email_sended_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pr_email_sended_email_sended_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    242            �            1259    24808    pr_email_type    TABLE     �   CREATE TABLE public.pr_email_type (
    email_type_key integer NOT NULL,
    description character varying(255) NOT NULL,
    text character varying
);
 !   DROP TABLE public.pr_email_type;
       public         heap    postgres    false            �            1259    24813     pr_email_type_email_type_key_seq    SEQUENCE     �   ALTER TABLE public.pr_email_type ALTER COLUMN email_type_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pr_email_type_email_type_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    244            �            1259    24814    pr_order    TABLE       CREATE TABLE public.pr_order (
    order_key bigint NOT NULL,
    order_id_ext integer NOT NULL,
    created_at timestamp with time zone,
    payment_method "char",
    payer_id_ext integer,
    payer_key bigint,
    order_date timestamp with time zone,
    description character varying(255),
    order_ext_payment_id character varying(255),
    transaction_total double precision,
    order_type_key integer,
    allocator_key integer,
    transaction_fee double precision,
    transaction_vat double precision,
    currency character(5)
);
    DROP TABLE public.pr_order;
       public         heap    postgres    false            �            1259    24819    pr_order_order_key_seq    SEQUENCE     �   ALTER TABLE public.pr_order ALTER COLUMN order_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pr_order_order_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    246            �            1259    24820    pr_order_type    TABLE     �   CREATE TABLE public.pr_order_type (
    order_type_key bigint NOT NULL,
    description character varying,
    type character varying(255),
    is_ext bit(1)
);
 !   DROP TABLE public.pr_order_type;
       public         heap    postgres    false            �            1259    24825    pr_orderline_wallet_club    TABLE     �  CREATE TABLE public.pr_orderline_wallet_club (
    notes text,
    order_id_ext bigint NOT NULL,
    orderline_id_ext character varying(256),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    wallet_key bigint NOT NULL,
    order_key bigint,
    amount_total double precision,
    orderline_id_ext_long character varying,
    orderline_key integer NOT NULL,
    wallet_is_updated bit(1)
);
 ,   DROP TABLE public.pr_orderline_wallet_club;
       public         heap    postgres    false            �            1259    24830 6   pr_orderline_club_wallet_orderline_club_wallet_key_seq    SEQUENCE       ALTER TABLE public.pr_orderline_wallet_club ALTER COLUMN orderline_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pr_orderline_club_wallet_orderline_club_wallet_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    249            �            1259    24831    pr_orderline_contribution    TABLE     �  CREATE TABLE public.pr_orderline_contribution (
    notes text,
    order_id_ext bigint NOT NULL,
    orderline_id_ext character varying(256),
    quantity integer,
    orderline_key bigint NOT NULL,
    order_key bigint,
    transaction_line_total double precision,
    project_key integer,
    project_id_ext character varying,
    price double precision,
    orderline_id_ext_long character varying,
    orderline_giftcard_key integer,
    wallet_key integer,
    proof_name character varying,
    proof_date timestamp with time zone,
    latitude double precision,
    longitude double precision,
    proof_small character varying,
    proof_medium character varying,
    proof_large character varying,
    batch_key integer,
    proof_uploaded_datetime timestamp with time zone,
    transaction_line_fee double precision,
    transaction_line_vat double precision,
    loaded_at timestamp with time zone,
    unit_ref_line_item character varying
);
 -   DROP TABLE public.pr_orderline_contribution;
       public         heap    postgres    false            �            1259    24836    pr_orderline_giftcard    TABLE     �  CREATE TABLE public.pr_orderline_giftcard (
    notes text,
    order_id_ext bigint NOT NULL,
    orderline_id_ext character varying(256),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    orderline_key bigint NOT NULL,
    order_key bigint,
    amount_line double precision,
    orderline_id_ext_long character varying,
    quantity integer,
    expirated_at date,
    token character varying,
    unit_ref_line_item character varying
);
 )   DROP TABLE public.pr_orderline_giftcard;
       public         heap    postgres    false            �            1259    24841 0   pr_orderline_giftcard_orderline_giftcard_key_seq    SEQUENCE       ALTER TABLE public.pr_orderline_giftcard ALTER COLUMN orderline_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pr_orderline_giftcard_orderline_giftcard_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    252            �            1259    24842    pr_orderline_orderline_key_seq    SEQUENCE     �   ALTER TABLE public.pr_orderline_contribution ALTER COLUMN orderline_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pr_orderline_orderline_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    251            �            1259    24843    pr_orderline_xxx_dump    TABLE     �  CREATE TABLE public.pr_orderline_xxx_dump (
    id bigint,
    notes text,
    order_id bigint,
    product_id double precision,
    stripe_line_item_id text,
    unit_reference_in_line_item text,
    quantity text,
    batch_id text,
    is_order_confirmation_sent boolean,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    amount text,
    currency text,
    checked_date timestamp with time zone,
    is_ok bit(1)
);
 )   DROP TABLE public.pr_orderline_xxx_dump;
       public         heap    postgres    false                        1259    24848 
   pr_partner    TABLE     L  CREATE TABLE public.pr_partner (
    partner_key integer NOT NULL,
    name character varying,
    description character varying,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    country character(50),
    address character varying,
    email character varying,
    zipcode character varying
);
    DROP TABLE public.pr_partner;
       public         heap    postgres    false                       1259    24853    pr_partner_partner_key_seq    SEQUENCE     �   ALTER TABLE public.pr_partner ALTER COLUMN partner_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pr_partner_partner_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    256                       1259    24854    pr_payer    TABLE     @  CREATE TABLE public.pr_payer (
    payer_id_ext bigint,
    email character varying(255) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone,
    country character varying(255),
    type character varying(255),
    payer_key bigint NOT NULL,
    name character varying
);
    DROP TABLE public.pr_payer;
       public         heap    postgres    false                       1259    24859    pr_payer_ext    TABLE     �   CREATE TABLE public.pr_payer_ext (
    payer_ext_key bigint NOT NULL,
    payer_id_ext bigint,
    payer_id_ext_long character varying,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    payer_key bigint
);
     DROP TABLE public.pr_payer_ext;
       public         heap    postgres    false                       1259    24864    pr_payers_payer_key_seq    SEQUENCE     �   ALTER TABLE public.pr_payer ALTER COLUMN payer_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pr_payers_payer_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    258                       1259    24865 #   pr_payers_stripe_payer_strip_id_seq    SEQUENCE     �   ALTER TABLE public.pr_payer_ext ALTER COLUMN payer_ext_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pr_payers_stripe_payer_strip_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    259                       1259    24866    pr_project_type    TABLE     r   CREATE TABLE public.pr_project_type (
    project_type_key integer NOT NULL,
    description character varying
);
 #   DROP TABLE public.pr_project_type;
       public         heap    postgres    false                       1259    24871 $   pr_project_type_project_type_key_seq    SEQUENCE     �   ALTER TABLE public.pr_project_type ALTER COLUMN project_type_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pr_project_type_project_type_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    262                       1259    24872 	   pr_wallet    TABLE     �   CREATE TABLE public.pr_wallet (
    wallet_key bigint NOT NULL,
    balance double precision,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    payer_key integer,
    number_total integer
);
    DROP TABLE public.pr_wallet;
       public         heap    postgres    false            	           1259    24875    pr_wallet_wallet_key_seq    SEQUENCE     �   ALTER TABLE public.pr_wallet ALTER COLUMN wallet_key ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pr_wallet_wallet_key_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    264            
           1259    24876    xx_orderline_contribution_bckup    TABLE     �  CREATE TABLE public.xx_orderline_contribution_bckup (
    notes text,
    order_id_ext bigint NOT NULL,
    orderline_id_ext character varying(256),
    quantity integer,
    orderline_key bigint,
    order_key bigint,
    transaction_line_total double precision,
    project_key integer,
    project_id_ext character varying,
    price double precision,
    orderline_id_ext_long character varying,
    orderline_giftcard_key integer,
    wallet_key integer,
    proof_name character varying,
    proof_date timestamp with time zone,
    latitude double precision,
    longitude double precision,
    proof_small character varying,
    proof_medium character varying,
    proof_large character varying,
    batch_key integer,
    proof_uploaded_datetime timestamp with time zone,
    transaction_line_fee double precision,
    transaction_line_vat double precision,
    created_at timestamp with time zone,
    unit_ref_line_item character varying
);
 3   DROP TABLE public.xx_orderline_contribution_bckup;
       public         heap    postgres    false                      0    24698 
   auth_group 
   TABLE DATA           .   COPY public.auth_group (id, name) FROM stdin;
    public          postgres    false    210   �_                0    24701    auth_group_permissions 
   TABLE DATA           M   COPY public.auth_group_permissions (id, group_id, permission_id) FROM stdin;
    public          postgres    false    211   `                0    24704    auth_permission 
   TABLE DATA           N   COPY public.auth_permission (id, name, content_type_id, codename) FROM stdin;
    public          postgres    false    212   7`                0    24708 	   auth_user 
   TABLE DATA           �   COPY public.auth_user (id, password, last_login, is_superuser, username, first_name, last_name, email, is_staff, is_active, date_joined) FROM stdin;
    public          postgres    false    214   T`                0    24713    auth_user_groups 
   TABLE DATA           A   COPY public.auth_user_groups (id, user_id, group_id) FROM stdin;
    public          postgres    false    215   q`                0    24718    auth_user_user_permissions 
   TABLE DATA           P   COPY public.auth_user_user_permissions (id, user_id, permission_id) FROM stdin;
    public          postgres    false    218   �`      !          0    24728    log_job 
   TABLE DATA           e   COPY public.log_job (jobname, jobtype, jobfunction, startdatetime, enddatetime, job_key) FROM stdin;
    public          postgres    false    222   �`      #          0    24734    log_zip_images 
   TABLE DATA           V   COPY public.log_zip_images (batch_key, zipname, extractdatetime, zip_key) FROM stdin;
    public          postgres    false    224   ^a      %          0    24740    lz_b2b_orderlines 
   TABLE DATA           �  COPY public.lz_b2b_orderlines ("ORDERNUMBER", "ORDERLINE", "MAIL", "NAME", "PROJECT", "ORDERDATE", "QUANTITY", "BATCH ID", "Sort payment", "PRICE-EXFEE", "FEE-EXBTW", "VAT", "FEE", "TOTAL-EXFEE", "TOTAL", "STATUS", "LAST UPDATE", "CURRENT DAT (HIDDEN)", "NEED TO SENT AN EMAIL", "Merged Doc ID - Sent MAILS ORDER PLACED", "Merged Doc URL - Sent MAILS ORDER PLACED", "Link to merged Doc - Sent MAILS ORDER PLACED", "Document Merge Status - Sent MAILS ORDER PLACED", "Filter Rows to Merge") FROM stdin;
    public          postgres    false    226   �a      &          0    24745    lz_b2b_orders 
   TABLE DATA           �  COPY public.lz_b2b_orders ("ORDERNUMBER", "MAIL", "NAME", "PROJECT", "ORDERDATE", "QUANTITY", "BATCH ID", "Sort payment", "PRICE-EXFEE", "FEE-EXBTW", "VAT", "FEE", "TOTAL-EXFEE", "TOTAL", "STATUS", "LAST UPDATE", "CURRENT DAT (HIDDEN)", "NEED TO SENT AN EMAIL", "Merged Doc ID - Sent MAILS ORDER PLACED", "Merged Doc URL - Sent MAILS ORDER PLACED", "Link to merged Doc - Sent MAILS ORDER PLACED", "Document Merge Status - Sent MAILS ORDER PLACED", "Filter Rows to Merge") FROM stdin;
    public          postgres    false    227   yx      '          0    24750    lz_batches_batch 
   TABLE DATA           *   COPY public.lz_batches_batch  FROM stdin;
    public          postgres    false    228   }      (          0    24753    lz_customers_customer 
   TABLE DATA           p   COPY public.lz_customers_customer (id, email, country, customer_type, name, created_at, updated_at) FROM stdin;
    public          postgres    false    229   5}      )          0    24758    lz_customers_stripecustomer 
   TABLE DATA           n   COPY public.lz_customers_stripecustomer (stripe_customer_id, customer_id, created_at, updated_at) FROM stdin;
    public          postgres    false    230   Ԉ      *          0    24763    lz_image_proof 
   TABLE DATA           e   COPY public.lz_image_proof (filename, batch_key, filedatetime, latitude, longitude, url) FROM stdin;
    public          postgres    false    231   ٖ      +          0    24768    lz_orders_order 
   TABLE DATA           �   COPY public.lz_orders_order (id, order_date, customer_id, stripe_payment_intent_id, order_description, created_at, updated_at, amount, currency) FROM stdin;
    public          postgres    false    232   7�      ,          0    24773    lz_orders_orderitem 
   TABLE DATA           �   COPY public.lz_orders_orderitem (id, notes, order_id, product_id, stripe_line_item_id, unit_reference_in_line_item, quantity, batch_id, is_order_confirmation_sent, created_at, updated_at, amount, currency) FROM stdin;
    public          postgres    false    233   N�      -          0    24778    lz_products_product 
   TABLE DATA           �   COPY public.lz_products_product (id, title, description, stripe_product_id, webflow_product_id, product_type, created_at, updated_at) FROM stdin;
    public          postgres    false    234   #�      .          0    24783    pr_allocator 
   TABLE DATA           _   COPY public.pr_allocator (allocator_key, name, email, is_ext, created_at, country) FROM stdin;
    public          postgres    false    235   ��      /          0    24788    pr_batch 
   TABLE DATA           �   COPY public.pr_batch (batch_key, created_at, updated_at, text_planned, text_completed, status, batch_size, project_key, batch_invoice_key) FROM stdin;
    public          postgres    false    236   ��      1          0    24794    pr_batch_invoice 
   TABLE DATA           y   COPY public.pr_batch_invoice (batch_invoice_key, amount_total, completed_at, invoice_nr, invoice_unit_price) FROM stdin;
    public          postgres    false    238   l�      5          0    24804    pr_email_sended 
   TABLE DATA           e   COPY public.pr_email_sended (email_sended_key, sended_at, email_type_key, orderline_key) FROM stdin;
    public          postgres    false    242   ��      7          0    24808    pr_email_type 
   TABLE DATA           J   COPY public.pr_email_type (email_type_key, description, text) FROM stdin;
    public          postgres    false    244   ڧ      9          0    24814    pr_order 
   TABLE DATA           �   COPY public.pr_order (order_key, order_id_ext, created_at, payment_method, payer_id_ext, payer_key, order_date, description, order_ext_payment_id, transaction_total, order_type_key, allocator_key, transaction_fee, transaction_vat, currency) FROM stdin;
    public          postgres    false    246   ��      ;          0    24820    pr_order_type 
   TABLE DATA           R   COPY public.pr_order_type (order_type_key, description, type, is_ext) FROM stdin;
    public          postgres    false    248   
�      >          0    24831    pr_orderline_contribution 
   TABLE DATA           �  COPY public.pr_orderline_contribution (notes, order_id_ext, orderline_id_ext, quantity, orderline_key, order_key, transaction_line_total, project_key, project_id_ext, price, orderline_id_ext_long, orderline_giftcard_key, wallet_key, proof_name, proof_date, latitude, longitude, proof_small, proof_medium, proof_large, batch_key, proof_uploaded_datetime, transaction_line_fee, transaction_line_vat, loaded_at, unit_ref_line_item) FROM stdin;
    public          postgres    false    251   v�      ?          0    24836    pr_orderline_giftcard 
   TABLE DATA           �   COPY public.pr_orderline_giftcard (notes, order_id_ext, orderline_id_ext, created_at, updated_at, orderline_key, order_key, amount_line, orderline_id_ext_long, quantity, expirated_at, token, unit_ref_line_item) FROM stdin;
    public          postgres    false    252   v�      <          0    24825    pr_orderline_wallet_club 
   TABLE DATA           �   COPY public.pr_orderline_wallet_club (notes, order_id_ext, orderline_id_ext, created_at, updated_at, wallet_key, order_key, amount_total, orderline_id_ext_long, orderline_key, wallet_is_updated) FROM stdin;
    public          postgres    false    249   �      B          0    24843    pr_orderline_xxx_dump 
   TABLE DATA           �   COPY public.pr_orderline_xxx_dump (id, notes, order_id, product_id, stripe_line_item_id, unit_reference_in_line_item, quantity, batch_id, is_order_confirmation_sent, created_at, updated_at, amount, currency, checked_date, is_ok) FROM stdin;
    public          postgres    false    255   ��      C          0    24848 
   pr_partner 
   TABLE DATA           ~   COPY public.pr_partner (partner_key, name, description, created_at, updated_at, country, address, email, zipcode) FROM stdin;
    public          postgres    false    256   ��      E          0    24854    pr_payer 
   TABLE DATA           o   COPY public.pr_payer (payer_id_ext, email, created_at, updated_at, country, type, payer_key, name) FROM stdin;
    public          postgres    false    258   Y�      F          0    24859    pr_payer_ext 
   TABLE DATA           y   COPY public.pr_payer_ext (payer_ext_key, payer_id_ext, payer_id_ext_long, created_at, updated_at, payer_key) FROM stdin;
    public          postgres    false    259   ��                0    24722 
   pr_project 
   TABLE DATA             COPY public.pr_project (project_key, description, description_long, unit_price, web_flow_id, project_id_ext, created_at, updated_at, project_id_ext_long, latitude, longitude, name, project_image_medium, partner_key, project_type_key, batch_size, fee) FROM stdin;
    public          postgres    false    220   �
      3          0    24800    pr_project_price 
   TABLE DATA           |   COPY public.pr_project_price (project_price, project_key, unit_price, unit_price_delta, created_at, is_current) FROM stdin;
    public          postgres    false    240   @      I          0    24866    pr_project_type 
   TABLE DATA           H   COPY public.pr_project_type (project_type_key, description) FROM stdin;
    public          postgres    false    262   ]      K          0    24872 	   pr_wallet 
   TABLE DATA           i   COPY public.pr_wallet (wallet_key, balance, created_at, updated_at, payer_key, number_total) FROM stdin;
    public          postgres    false    264   z      M          0    24876    xx_orderline_contribution_bckup 
   TABLE DATA           �  COPY public.xx_orderline_contribution_bckup (notes, order_id_ext, orderline_id_ext, quantity, orderline_key, order_key, transaction_line_total, project_key, project_id_ext, price, orderline_id_ext_long, orderline_giftcard_key, wallet_key, proof_name, proof_date, latitude, longitude, proof_small, proof_medium, proof_large, batch_key, proof_uploaded_datetime, transaction_line_fee, transaction_line_vat, created_at, unit_ref_line_item) FROM stdin;
    public          postgres    false    266   @      V           0    0    auth_permission_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.auth_permission_id_seq', 1, false);
          public          postgres    false    213            W           0    0    auth_user_groups_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.auth_user_groups_id_seq', 1, false);
          public          postgres    false    216            X           0    0    auth_user_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.auth_user_id_seq', 1, false);
          public          postgres    false    217            Y           0    0 !   auth_user_user_permissions_id_seq    SEQUENCE SET     P   SELECT pg_catalog.setval('public.auth_user_user_permissions_id_seq', 1, false);
          public          postgres    false    219            Z           0    0 !   contribution_contribution_key_seq    SEQUENCE SET     P   SELECT pg_catalog.setval('public.contribution_contribution_key_seq', 28, true);
          public          postgres    false    221            [           0    0    log_job_job_key_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.log_job_job_key_seq', 9, true);
          public          postgres    false    223            \           0    0    log_zip_images_zip_key_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.log_zip_images_zip_key_seq', 1, true);
          public          postgres    false    225            ]           0    0    pr_batch_batch_key_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.pr_batch_batch_key_seq', 1, true);
          public          postgres    false    237            ^           0    0 &   pr_batch_invoice_batch_invoice_key_seq    SEQUENCE SET     U   SELECT pg_catalog.setval('public.pr_batch_invoice_batch_invoice_key_seq', 1, false);
          public          postgres    false    239            _           0    0 ,   pr_contribution_price_contribution_price_seq    SEQUENCE SET     [   SELECT pg_catalog.setval('public.pr_contribution_price_contribution_price_seq', 1, false);
          public          postgres    false    241            `           0    0 $   pr_email_sended_email_sended_key_seq    SEQUENCE SET     S   SELECT pg_catalog.setval('public.pr_email_sended_email_sended_key_seq', 1, false);
          public          postgres    false    243            a           0    0     pr_email_type_email_type_key_seq    SEQUENCE SET     O   SELECT pg_catalog.setval('public.pr_email_type_email_type_key_seq', 1, false);
          public          postgres    false    245            b           0    0    pr_order_order_key_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.pr_order_order_key_seq', 681, true);
          public          postgres    false    247            c           0    0 6   pr_orderline_club_wallet_orderline_club_wallet_key_seq    SEQUENCE SET     e   SELECT pg_catalog.setval('public.pr_orderline_club_wallet_orderline_club_wallet_key_seq', 36, true);
          public          postgres    false    250            d           0    0 0   pr_orderline_giftcard_orderline_giftcard_key_seq    SEQUENCE SET     ^   SELECT pg_catalog.setval('public.pr_orderline_giftcard_orderline_giftcard_key_seq', 3, true);
          public          postgres    false    253            e           0    0    pr_orderline_orderline_key_seq    SEQUENCE SET     O   SELECT pg_catalog.setval('public.pr_orderline_orderline_key_seq', 1638, true);
          public          postgres    false    254            f           0    0    pr_partner_partner_key_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.pr_partner_partner_key_seq', 21, true);
          public          postgres    false    257            g           0    0    pr_payers_payer_key_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.pr_payers_payer_key_seq', 904, true);
          public          postgres    false    260            h           0    0 #   pr_payers_stripe_payer_strip_id_seq    SEQUENCE SET     S   SELECT pg_catalog.setval('public.pr_payers_stripe_payer_strip_id_seq', 571, true);
          public          postgres    false    261            i           0    0 $   pr_project_type_project_type_key_seq    SEQUENCE SET     S   SELECT pg_catalog.setval('public.pr_project_type_project_type_key_seq', 1, false);
          public          postgres    false    263            j           0    0    pr_wallet_wallet_key_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.pr_wallet_wallet_key_seq', 13, true);
          public          postgres    false    265            >           2606    24882    pr_allocator allocator_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.pr_allocator
    ADD CONSTRAINT allocator_pkey PRIMARY KEY (allocator_key);
 E   ALTER TABLE ONLY public.pr_allocator DROP CONSTRAINT allocator_pkey;
       public            postgres    false    235                       2606    24884    auth_group auth_group_name_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.auth_group
    ADD CONSTRAINT auth_group_name_key UNIQUE (name);
 H   ALTER TABLE ONLY public.auth_group DROP CONSTRAINT auth_group_name_key;
       public            postgres    false    210            !           2606    24886 R   auth_group_permissions auth_group_permissions_group_id_permission_id_0cd325b0_uniq 
   CONSTRAINT     �   ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_group_id_permission_id_0cd325b0_uniq UNIQUE (group_id, permission_id);
 |   ALTER TABLE ONLY public.auth_group_permissions DROP CONSTRAINT auth_group_permissions_group_id_permission_id_0cd325b0_uniq;
       public            postgres    false    211    211            $           2606    24888 2   auth_group_permissions auth_group_permissions_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_pkey PRIMARY KEY (id);
 \   ALTER TABLE ONLY public.auth_group_permissions DROP CONSTRAINT auth_group_permissions_pkey;
       public            postgres    false    211                       2606    24890    auth_group auth_group_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.auth_group
    ADD CONSTRAINT auth_group_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.auth_group DROP CONSTRAINT auth_group_pkey;
       public            postgres    false    210            '           2606    24892 $   auth_permission auth_permission_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.auth_permission
    ADD CONSTRAINT auth_permission_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.auth_permission DROP CONSTRAINT auth_permission_pkey;
       public            postgres    false    212            /           2606    24894 &   auth_user_groups auth_user_groups_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_pkey PRIMARY KEY (id);
 P   ALTER TABLE ONLY public.auth_user_groups DROP CONSTRAINT auth_user_groups_pkey;
       public            postgres    false    215            2           2606    24896 @   auth_user_groups auth_user_groups_user_id_group_id_94350c0c_uniq 
   CONSTRAINT     �   ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_user_id_group_id_94350c0c_uniq UNIQUE (user_id, group_id);
 j   ALTER TABLE ONLY public.auth_user_groups DROP CONSTRAINT auth_user_groups_user_id_group_id_94350c0c_uniq;
       public            postgres    false    215    215            )           2606    24898    auth_user auth_user_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.auth_user
    ADD CONSTRAINT auth_user_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.auth_user DROP CONSTRAINT auth_user_pkey;
       public            postgres    false    214            5           2606    24900 :   auth_user_user_permissions auth_user_user_permissions_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_pkey PRIMARY KEY (id);
 d   ALTER TABLE ONLY public.auth_user_user_permissions DROP CONSTRAINT auth_user_user_permissions_pkey;
       public            postgres    false    218            8           2606    24902 Y   auth_user_user_permissions auth_user_user_permissions_user_id_permission_id_14a6b632_uniq 
   CONSTRAINT     �   ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_user_id_permission_id_14a6b632_uniq UNIQUE (user_id, permission_id);
 �   ALTER TABLE ONLY public.auth_user_user_permissions DROP CONSTRAINT auth_user_user_permissions_user_id_permission_id_14a6b632_uniq;
       public            postgres    false    218    218            ,           2606    24904     auth_user auth_user_username_key 
   CONSTRAINT     _   ALTER TABLE ONLY public.auth_user
    ADD CONSTRAINT auth_user_username_key UNIQUE (username);
 J   ALTER TABLE ONLY public.auth_user DROP CONSTRAINT auth_user_username_key;
       public            postgres    false    214            C           2606    24906 #   pr_batch_invoice batch_invoice_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY public.pr_batch_invoice
    ADD CONSTRAINT batch_invoice_pkey PRIMARY KEY (batch_invoice_key);
 M   ALTER TABLE ONLY public.pr_batch_invoice DROP CONSTRAINT batch_invoice_pkey;
       public            postgres    false    238            @           2606    24908    pr_batch batch_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.pr_batch
    ADD CONSTRAINT batch_pkey PRIMARY KEY (batch_key);
 =   ALTER TABLE ONLY public.pr_batch DROP CONSTRAINT batch_pkey;
       public            postgres    false    236            H           2606    24910 !   pr_email_sended email_sended_pkey 
   CONSTRAINT     m   ALTER TABLE ONLY public.pr_email_sended
    ADD CONSTRAINT email_sended_pkey PRIMARY KEY (email_sended_key);
 K   ALTER TABLE ONLY public.pr_email_sended DROP CONSTRAINT email_sended_pkey;
       public            postgres    false    242            L           2606    24912    pr_email_type email_type_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY public.pr_email_type
    ADD CONSTRAINT email_type_pkey PRIMARY KEY (email_type_key);
 G   ALTER TABLE ONLY public.pr_email_type DROP CONSTRAINT email_type_pkey;
       public            postgres    false    244            R           2606    24914    pr_order order_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.pr_order
    ADD CONSTRAINT order_pkey PRIMARY KEY (order_key);
 =   ALTER TABLE ONLY public.pr_order DROP CONSTRAINT order_pkey;
       public            postgres    false    246            T           2606    24916    pr_order_type order_type_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY public.pr_order_type
    ADD CONSTRAINT order_type_pkey PRIMARY KEY (order_type_key);
 G   ALTER TABLE ONLY public.pr_order_type DROP CONSTRAINT order_type_pkey;
       public            postgres    false    248            b           2606    24918 -   pr_orderline_giftcard orderline_giftcard_pkey 
   CONSTRAINT     v   ALTER TABLE ONLY public.pr_orderline_giftcard
    ADD CONSTRAINT orderline_giftcard_pkey PRIMARY KEY (orderline_key);
 W   ALTER TABLE ONLY public.pr_orderline_giftcard DROP CONSTRAINT orderline_giftcard_pkey;
       public            postgres    false    252            _           2606    24920 (   pr_orderline_contribution orderline_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY public.pr_orderline_contribution
    ADD CONSTRAINT orderline_pkey PRIMARY KEY (orderline_key);
 R   ALTER TABLE ONLY public.pr_orderline_contribution DROP CONSTRAINT orderline_pkey;
       public            postgres    false    251            X           2606    24922 3   pr_orderline_wallet_club orderline_wallet_club_pkey 
   CONSTRAINT     |   ALTER TABLE ONLY public.pr_orderline_wallet_club
    ADD CONSTRAINT orderline_wallet_club_pkey PRIMARY KEY (orderline_key);
 ]   ALTER TABLE ONLY public.pr_orderline_wallet_club DROP CONSTRAINT orderline_wallet_club_pkey;
       public            postgres    false    249            d           2606    24924    pr_partner partner_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.pr_partner
    ADD CONSTRAINT partner_pkey PRIMARY KEY (partner_key);
 A   ALTER TABLE ONLY public.pr_partner DROP CONSTRAINT partner_pkey;
       public            postgres    false    256            k           2606    24926    pr_payer_ext payer_ext_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.pr_payer_ext
    ADD CONSTRAINT payer_ext_pkey PRIMARY KEY (payer_ext_key);
 E   ALTER TABLE ONLY public.pr_payer_ext DROP CONSTRAINT payer_ext_pkey;
       public            postgres    false    259            f           2606    24928    pr_payer payer_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.pr_payer
    ADD CONSTRAINT payer_pkey PRIMARY KEY (payer_key);
 =   ALTER TABLE ONLY public.pr_payer DROP CONSTRAINT payer_pkey;
       public            postgres    false    258            <           2606    24930    pr_project project_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.pr_project
    ADD CONSTRAINT project_pkey PRIMARY KEY (project_key);
 A   ALTER TABLE ONLY public.pr_project DROP CONSTRAINT project_pkey;
       public            postgres    false    220            F           2606    24932 #   pr_project_price project_price_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.pr_project_price
    ADD CONSTRAINT project_price_pkey PRIMARY KEY (project_price);
 M   ALTER TABLE ONLY public.pr_project_price DROP CONSTRAINT project_price_pkey;
       public            postgres    false    240            m           2606    24934 !   pr_project_type project_type_pkey 
   CONSTRAINT     m   ALTER TABLE ONLY public.pr_project_type
    ADD CONSTRAINT project_type_pkey PRIMARY KEY (project_type_key);
 K   ALTER TABLE ONLY public.pr_project_type DROP CONSTRAINT project_type_pkey;
       public            postgres    false    262            h           2606    24938    pr_payer unique_payer_id_django 
   CONSTRAINT     b   ALTER TABLE ONLY public.pr_payer
    ADD CONSTRAINT unique_payer_id_django UNIQUE (payer_id_ext);
 I   ALTER TABLE ONLY public.pr_payer DROP CONSTRAINT unique_payer_id_django;
       public            postgres    false    258            p           2606    24940    pr_wallet wallet_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY public.pr_wallet
    ADD CONSTRAINT wallet_pkey PRIMARY KEY (wallet_key);
 ?   ALTER TABLE ONLY public.pr_wallet DROP CONSTRAINT wallet_pkey;
       public            postgres    false    264                       1259    24941    auth_group_name_a6ea08ec_like    INDEX     h   CREATE INDEX auth_group_name_a6ea08ec_like ON public.auth_group USING btree (name varchar_pattern_ops);
 1   DROP INDEX public.auth_group_name_a6ea08ec_like;
       public            postgres    false    210                       1259    24942 (   auth_group_permissions_group_id_b120cbf9    INDEX     o   CREATE INDEX auth_group_permissions_group_id_b120cbf9 ON public.auth_group_permissions USING btree (group_id);
 <   DROP INDEX public.auth_group_permissions_group_id_b120cbf9;
       public            postgres    false    211            "           1259    24943 -   auth_group_permissions_permission_id_84c5c92e    INDEX     y   CREATE INDEX auth_group_permissions_permission_id_84c5c92e ON public.auth_group_permissions USING btree (permission_id);
 A   DROP INDEX public.auth_group_permissions_permission_id_84c5c92e;
       public            postgres    false    211            %           1259    24944 (   auth_permission_content_type_id_2f476e4b    INDEX     o   CREATE INDEX auth_permission_content_type_id_2f476e4b ON public.auth_permission USING btree (content_type_id);
 <   DROP INDEX public.auth_permission_content_type_id_2f476e4b;
       public            postgres    false    212            -           1259    24945 "   auth_user_groups_group_id_97559544    INDEX     c   CREATE INDEX auth_user_groups_group_id_97559544 ON public.auth_user_groups USING btree (group_id);
 6   DROP INDEX public.auth_user_groups_group_id_97559544;
       public            postgres    false    215            0           1259    24946 !   auth_user_groups_user_id_6a12ed8b    INDEX     a   CREATE INDEX auth_user_groups_user_id_6a12ed8b ON public.auth_user_groups USING btree (user_id);
 5   DROP INDEX public.auth_user_groups_user_id_6a12ed8b;
       public            postgres    false    215            3           1259    24947 1   auth_user_user_permissions_permission_id_1fbb5f2c    INDEX     �   CREATE INDEX auth_user_user_permissions_permission_id_1fbb5f2c ON public.auth_user_user_permissions USING btree (permission_id);
 E   DROP INDEX public.auth_user_user_permissions_permission_id_1fbb5f2c;
       public            postgres    false    218            6           1259    24948 +   auth_user_user_permissions_user_id_a95ead1b    INDEX     u   CREATE INDEX auth_user_user_permissions_user_id_a95ead1b ON public.auth_user_user_permissions USING btree (user_id);
 ?   DROP INDEX public.auth_user_user_permissions_user_id_a95ead1b;
       public            postgres    false    218            *           1259    24949     auth_user_username_6821ab7c_like    INDEX     n   CREATE INDEX auth_user_username_6821ab7c_like ON public.auth_user USING btree (username varchar_pattern_ops);
 4   DROP INDEX public.auth_user_username_6821ab7c_like;
       public            postgres    false    214            A           1259    24950    fki_batch_contribution    INDEX     R   CREATE INDEX fki_batch_contribution ON public.pr_batch USING btree (project_key);
 *   DROP INDEX public.fki_batch_contribution;
       public            postgres    false    236            D           1259    24951 #   fki_contribution_price_contribution    INDEX     g   CREATE INDEX fki_contribution_price_contribution ON public.pr_project_price USING btree (project_key);
 7   DROP INDEX public.fki_contribution_price_contribution;
       public            postgres    false    240            I           1259    24952    fki_email_sended_email_type    INDEX     a   CREATE INDEX fki_email_sended_email_type ON public.pr_email_sended USING btree (email_type_key);
 /   DROP INDEX public.fki_email_sended_email_type;
       public            postgres    false    242            J           1259    24953    fki_email_sended_orderline    INDEX     _   CREATE INDEX fki_email_sended_orderline ON public.pr_email_sended USING btree (orderline_key);
 .   DROP INDEX public.fki_email_sended_orderline;
       public            postgres    false    242            M           1259    24954    fki_i    INDEX     C   CREATE INDEX fki_i ON public.pr_order USING btree (allocator_key);
    DROP INDEX public.fki_i;
       public            postgres    false    246            Y           1259    24955    fki_order_line_order    INDEX     _   CREATE INDEX fki_order_line_order ON public.pr_orderline_contribution USING btree (order_key);
 (   DROP INDEX public.fki_order_line_order;
       public            postgres    false    251            N           1259    24956    fki_order_order_type    INDEX     S   CREATE INDEX fki_order_order_type ON public.pr_order USING btree (order_type_key);
 (   DROP INDEX public.fki_order_order_type;
       public            postgres    false    246            O           1259    24957    fki_order_payer    INDEX     I   CREATE INDEX fki_order_payer ON public.pr_order USING btree (payer_key);
 #   DROP INDEX public.fki_order_payer;
       public            postgres    false    246            Z           1259    24958    fki_orderline_batch    INDEX     ^   CREATE INDEX fki_orderline_batch ON public.pr_orderline_contribution USING btree (batch_key);
 '   DROP INDEX public.fki_orderline_batch;
       public            postgres    false    251            U           1259    24959     fki_orderline_club_wallet_wallet    INDEX     k   CREATE INDEX fki_orderline_club_wallet_wallet ON public.pr_orderline_wallet_club USING btree (wallet_key);
 4   DROP INDEX public.fki_orderline_club_wallet_wallet;
       public            postgres    false    249            [           1259    24960    fki_orderline_contribution    INDEX     g   CREATE INDEX fki_orderline_contribution ON public.pr_orderline_contribution USING btree (project_key);
 .   DROP INDEX public.fki_orderline_contribution;
       public            postgres    false    251            `           1259    24961    fki_orderline_giftcard_order    INDEX     c   CREATE INDEX fki_orderline_giftcard_order ON public.pr_orderline_giftcard USING btree (order_key);
 0   DROP INDEX public.fki_orderline_giftcard_order;
       public            postgres    false    252            \           1259    24962     fki_orderline_orderline_giftcard    INDEX     x   CREATE INDEX fki_orderline_orderline_giftcard ON public.pr_orderline_contribution USING btree (orderline_giftcard_key);
 4   DROP INDEX public.fki_orderline_orderline_giftcard;
       public            postgres    false    251            ]           1259    24963    fki_orderline_wallet    INDEX     `   CREATE INDEX fki_orderline_wallet ON public.pr_orderline_contribution USING btree (wallet_key);
 (   DROP INDEX public.fki_orderline_wallet;
       public            postgres    false    251            V           1259    24964    fki_orderline_wallet_club_order    INDEX     i   CREATE INDEX fki_orderline_wallet_club_order ON public.pr_orderline_wallet_club USING btree (order_key);
 3   DROP INDEX public.fki_orderline_wallet_club_order;
       public            postgres    false    249            P           1259    24965    fki_payed by Payer    INDEX     N   CREATE INDEX "fki_payed by Payer" ON public.pr_order USING btree (payer_key);
 (   DROP INDEX public."fki_payed by Payer";
       public            postgres    false    246            i           1259    24966    fki_payer_stripe_payer    INDEX     T   CREATE INDEX fki_payer_stripe_payer ON public.pr_payer_ext USING btree (payer_key);
 *   DROP INDEX public.fki_payer_stripe_payer;
       public            postgres    false    259            9           1259    24967    fki_project_partner    INDEX     Q   CREATE INDEX fki_project_partner ON public.pr_project USING btree (partner_key);
 '   DROP INDEX public.fki_project_partner;
       public            postgres    false    220            :           1259    24968    fki_project_project_type    INDEX     [   CREATE INDEX fki_project_project_type ON public.pr_project USING btree (project_type_key);
 ,   DROP INDEX public.fki_project_project_type;
       public            postgres    false    220            n           1259    24969    fki_wallet_payer    INDEX     K   CREATE INDEX fki_wallet_payer ON public.pr_wallet USING btree (payer_key);
 $   DROP INDEX public.fki_wallet_payer;
       public            postgres    false    264            q           2606    24970 O   auth_group_permissions auth_group_permissio_permission_id_84c5c92e_fk_auth_perm    FK CONSTRAINT     �   ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissio_permission_id_84c5c92e_fk_auth_perm FOREIGN KEY (permission_id) REFERENCES public.auth_permission(id) DEFERRABLE INITIALLY DEFERRED;
 y   ALTER TABLE ONLY public.auth_group_permissions DROP CONSTRAINT auth_group_permissio_permission_id_84c5c92e_fk_auth_perm;
       public          postgres    false    212    3623    211            r           2606    24975 P   auth_group_permissions auth_group_permissions_group_id_b120cbf9_fk_auth_group_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.auth_group_permissions
    ADD CONSTRAINT auth_group_permissions_group_id_b120cbf9_fk_auth_group_id FOREIGN KEY (group_id) REFERENCES public.auth_group(id) DEFERRABLE INITIALLY DEFERRED;
 z   ALTER TABLE ONLY public.auth_group_permissions DROP CONSTRAINT auth_group_permissions_group_id_b120cbf9_fk_auth_group_id;
       public          postgres    false    3614    210    211            s           2606    24980 D   auth_user_groups auth_user_groups_group_id_97559544_fk_auth_group_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_group_id_97559544_fk_auth_group_id FOREIGN KEY (group_id) REFERENCES public.auth_group(id) DEFERRABLE INITIALLY DEFERRED;
 n   ALTER TABLE ONLY public.auth_user_groups DROP CONSTRAINT auth_user_groups_group_id_97559544_fk_auth_group_id;
       public          postgres    false    215    210    3614            t           2606    24985 B   auth_user_groups auth_user_groups_user_id_6a12ed8b_fk_auth_user_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.auth_user_groups
    ADD CONSTRAINT auth_user_groups_user_id_6a12ed8b_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;
 l   ALTER TABLE ONLY public.auth_user_groups DROP CONSTRAINT auth_user_groups_user_id_6a12ed8b_fk_auth_user_id;
       public          postgres    false    215    3625    214            u           2606    24990 S   auth_user_user_permissions auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm    FK CONSTRAINT     �   ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm FOREIGN KEY (permission_id) REFERENCES public.auth_permission(id) DEFERRABLE INITIALLY DEFERRED;
 }   ALTER TABLE ONLY public.auth_user_user_permissions DROP CONSTRAINT auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm;
       public          postgres    false    212    218    3623            v           2606    24995 V   auth_user_user_permissions auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.auth_user_user_permissions
    ADD CONSTRAINT auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id FOREIGN KEY (user_id) REFERENCES public.auth_user(id) DEFERRABLE INITIALLY DEFERRED;
 �   ALTER TABLE ONLY public.auth_user_user_permissions DROP CONSTRAINT auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id;
       public          postgres    false    3625    218    214            y           2606    25000    pr_batch batch_project    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_batch
    ADD CONSTRAINT batch_project FOREIGN KEY (project_key) REFERENCES public.pr_project(project_key) NOT VALID;
 @   ALTER TABLE ONLY public.pr_batch DROP CONSTRAINT batch_project;
       public          postgres    false    3644    220    236            {           2606    25005 '   pr_email_sended email_sended_email_type    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_email_sended
    ADD CONSTRAINT email_sended_email_type FOREIGN KEY (email_type_key) REFERENCES public.pr_email_type(email_type_key) NOT VALID;
 Q   ALTER TABLE ONLY public.pr_email_sended DROP CONSTRAINT email_sended_email_type;
       public          postgres    false    244    242    3660            |           2606    25010 &   pr_email_sended email_sended_orderline    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_email_sended
    ADD CONSTRAINT email_sended_orderline FOREIGN KEY (orderline_key) REFERENCES public.pr_orderline_contribution(orderline_key) NOT VALID;
 P   ALTER TABLE ONLY public.pr_email_sended DROP CONSTRAINT email_sended_orderline;
       public          postgres    false    242    251    3679            }           2606    25015    pr_order order_allocator    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_order
    ADD CONSTRAINT order_allocator FOREIGN KEY (allocator_key) REFERENCES public.pr_allocator(allocator_key) NOT VALID;
 B   ALTER TABLE ONLY public.pr_order DROP CONSTRAINT order_allocator;
       public          postgres    false    246    3646    235            ~           2606    25020    pr_order order_order_type    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_order
    ADD CONSTRAINT order_order_type FOREIGN KEY (order_type_key) REFERENCES public.pr_order_type(order_type_key) NOT VALID;
 C   ALTER TABLE ONLY public.pr_order DROP CONSTRAINT order_order_type;
       public          postgres    false    3668    248    246                       2606    25025    pr_order order_payer    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_order
    ADD CONSTRAINT order_payer FOREIGN KEY (payer_key) REFERENCES public.pr_payer(payer_key) NOT VALID;
 >   ALTER TABLE ONLY public.pr_order DROP CONSTRAINT order_payer;
       public          postgres    false    3686    246    258            �           2606    25030 )   pr_orderline_contribution orderline_batch    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_orderline_contribution
    ADD CONSTRAINT orderline_batch FOREIGN KEY (batch_key) REFERENCES public.pr_batch(batch_key) NOT VALID;
 S   ALTER TABLE ONLY public.pr_orderline_contribution DROP CONSTRAINT orderline_batch;
       public          postgres    false    3648    236    251            �           2606    25035 .   pr_orderline_giftcard orderline_giftcard_order    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_orderline_giftcard
    ADD CONSTRAINT orderline_giftcard_order FOREIGN KEY (order_key) REFERENCES public.pr_order(order_key) NOT VALID;
 X   ALTER TABLE ONLY public.pr_orderline_giftcard DROP CONSTRAINT orderline_giftcard_order;
       public          postgres    false    246    252    3666            �           2606    25040 )   pr_orderline_contribution orderline_order    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_orderline_contribution
    ADD CONSTRAINT orderline_order FOREIGN KEY (order_key) REFERENCES public.pr_order(order_key) NOT VALID;
 S   ALTER TABLE ONLY public.pr_orderline_contribution DROP CONSTRAINT orderline_order;
       public          postgres    false    251    246    3666            �           2606    25045 6   pr_orderline_contribution orderline_orderline_giftcard    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_orderline_contribution
    ADD CONSTRAINT orderline_orderline_giftcard FOREIGN KEY (orderline_giftcard_key) REFERENCES public.pr_orderline_giftcard(orderline_key) NOT VALID;
 `   ALTER TABLE ONLY public.pr_orderline_contribution DROP CONSTRAINT orderline_orderline_giftcard;
       public          postgres    false    3682    252    251            �           2606    25050 +   pr_orderline_contribution orderline_project    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_orderline_contribution
    ADD CONSTRAINT orderline_project FOREIGN KEY (project_key) REFERENCES public.pr_project(project_key) NOT VALID;
 U   ALTER TABLE ONLY public.pr_orderline_contribution DROP CONSTRAINT orderline_project;
       public          postgres    false    3644    220    251            �           2606    25055 *   pr_orderline_contribution orderline_wallet    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_orderline_contribution
    ADD CONSTRAINT orderline_wallet FOREIGN KEY (wallet_key) REFERENCES public.pr_wallet(wallet_key) NOT VALID;
 T   ALTER TABLE ONLY public.pr_orderline_contribution DROP CONSTRAINT orderline_wallet;
       public          postgres    false    3696    264    251            �           2606    25060 4   pr_orderline_wallet_club orderline_wallet_club_order    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_orderline_wallet_club
    ADD CONSTRAINT orderline_wallet_club_order FOREIGN KEY (order_key) REFERENCES public.pr_order(order_key) NOT VALID;
 ^   ALTER TABLE ONLY public.pr_orderline_wallet_club DROP CONSTRAINT orderline_wallet_club_order;
       public          postgres    false    246    249    3666            �           2606    25065 5   pr_orderline_wallet_club orderline_wallet_club_wallet    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_orderline_wallet_club
    ADD CONSTRAINT orderline_wallet_club_wallet FOREIGN KEY (wallet_key) REFERENCES public.pr_wallet(wallet_key) NOT VALID;
 _   ALTER TABLE ONLY public.pr_orderline_wallet_club DROP CONSTRAINT orderline_wallet_club_wallet;
       public          postgres    false    249    3696    264            �           2606    25070    pr_payer_ext payer_ext_payer    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_payer_ext
    ADD CONSTRAINT payer_ext_payer FOREIGN KEY (payer_key) REFERENCES public.pr_payer(payer_key) NOT VALID;
 F   ALTER TABLE ONLY public.pr_payer_ext DROP CONSTRAINT payer_ext_payer;
       public          postgres    false    259    3686    258            w           2606    25075    pr_project project_partner    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_project
    ADD CONSTRAINT project_partner FOREIGN KEY (partner_key) REFERENCES public.pr_partner(partner_key) NOT VALID;
 D   ALTER TABLE ONLY public.pr_project DROP CONSTRAINT project_partner;
       public          postgres    false    3684    220    256            z           2606    25080 &   pr_project_price project_price_project    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_project_price
    ADD CONSTRAINT project_price_project FOREIGN KEY (project_key) REFERENCES public.pr_project(project_key) NOT VALID;
 P   ALTER TABLE ONLY public.pr_project_price DROP CONSTRAINT project_price_project;
       public          postgres    false    240    3644    220            x           2606    25085    pr_project project_project_type    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_project
    ADD CONSTRAINT project_project_type FOREIGN KEY (project_type_key) REFERENCES public.pr_project_type(project_type_key) NOT VALID;
 I   ALTER TABLE ONLY public.pr_project DROP CONSTRAINT project_project_type;
       public          postgres    false    262    3693    220            �           2606    25090    pr_wallet wallet_payer    FK CONSTRAINT     �   ALTER TABLE ONLY public.pr_wallet
    ADD CONSTRAINT wallet_payer FOREIGN KEY (payer_key) REFERENCES public.pr_payer(payer_key) NOT VALID;
 @   ALTER TABLE ONLY public.pr_wallet DROP CONSTRAINT wallet_payer;
       public          postgres    false    258    3686    264                  x������ � �            x������ � �            x������ � �            x������ � �            x������ � �            x������ � �      !   �   x��M
�0���)z���m&Yܹt)�� U7z+��@�������1��e��"4!"wn�av�0�����~wؗ��oz�q#����2*`l��7l�Z�,�e�A�0B�R�.hR^��sAS��36(Y,�b�DR�CDR�V��T�S���m�E      #   7   x�3�4�wJ,I��4202�5��50Q04�24�2��35�0�0�4�2!NY� �h�      %      x���}S�fچ��O�/p	�z�g��V6�&	�P���#�
����O��l�]H�L��"3�Ql���<�'3&s2{#��U���f�㺬�d:pvz�ް[V�a�V��dP3w���9Š��i�.�zX��rZ��~T�����O�Ŭ�����Q�e��ڃ�N��W8�Z`���1��D�f�餎1��'���1����I헳Ň[��娰Gv��t�ͩ�}��?�o���6����������x�h���8(vJgXח�����iySx��d0*��d��/G�����#�ݔ�o�=����!f��3�����=�]���vg׽^1���G�[�7-��:��}���b:h��n�����h�~��^w�~8Yw��?��nu�m�2��ܳ[{��}���rl7�/��o� v���f�&p7:�l��}��B8��C8<p�� �	���!�8dp���px�`|<�Ay�K�A{ L�A{�L�A{ M�A{�M�A{ N�A{�N�A{ O�A{�O�A�\}�}�}�}�}�}�}�}�}�}�/��'�=�'�=�'�=�'�=�'�=�'�=�'�=�'�=�'�=�'��9�>��>��>��>��>��>��>��>��>��>��V��I<h�I<h�I<h�I<h�I<h�I<h�I<h�I<h�I<h�I<�[�>��>��>��>��>��>��>��>��>��>��!�O�A{�O�A{�O�A{�O�A{�O�A{�O�A{�O�A{�O�A{�O�A{�O�Ay��x��x��x��x��x��x��x��x��x��xPr�$��$��$��$��$��$��$��$��$��$��f@ �ĉ�܈q��b4{5.��lt=?cN���ݨk{׭��؜���nU�c����d��N���'�<:��ǎ�E�GQ��s;���v⪞�g׋��|�b�R��ӣ��z|���}���4|r<�ت~}wy,_y��\9N��_��_|�={�.65���=5`���2j{F����?~���d:����W�I�<���߹�,����=+��첨�ϚK�ޤ�_>���o���z~s�M�m��7���Ƌ�pj/�Y�as�h���z�S����'���Ϸ��ηg�.�o��� �[:X���#<�w��y��oL�����y�  B� b� R� r���(@���@�b��(C6D��!
�Q �r�1�C���@�!
$��@�!
$��@�!
$��@�!
$��@�!
$��@B�!
$��@B�!
$��@B�!
$��@B�!
$��@B�!
$��@"�!
$��@"�!
$��@"�!
$��@"�!
$��@"�!
$��@b�!
$��@b�!
$��@b�!
$��@b�!
$��@b�!
$��@�!
$��@�!
$��@�!
$��@�!
$��@�!
$��@R�!
$��@R�!
$��@R�!
$��@R�!
$��@R�!
$��@2�!
$��@2�!
$��@2�!
$��@2�!
$��@2�!
$��@r�!
$��@r�!
$��@r�!
$��@r�!
$��@r�!
$��@��`����in�8�e1���U}6���5�s��sP����������������g�۟L��3s'}w���^�A�k5�
'Y3�bB;�Yp"/	߳/Fb/q��	C���ӵ���z�����Muwp��+������y4:ݾI>������/;�Ǳ��	�#'�U��z��λg�c�ż�_��G����}C�m����sߴy.����jKO�<�ֲc�͘Y3f���4���IUO˳k���w/^k��͏�ޟޘ�����������㻍Ag��9��)�Ϻ�OϚ�j��_v�������-���Y��e�6k�9͍���pV���D�F啝�9[�~ߋ�t1W�?�z
#/5v� w��	��q�WO7��}<윅{��w5�9��>^��,^NF������Ʌ��.|��_־q�h߀}[�oȾ��7b�V��o��Mط������}3�m��9��z_{���S�Z>0	��ӰZ>0��S�Z>0��ӱZ>0!��S��=p@�j�����L�j�����L�j�����L�j�����L�j����vR��9�ɝ�F���o�����3���Y���N��#Y�m�qsڽ+G������+���(yYh��L�|��ح�p���7i�\���ׇ�7�8]�>��wϏ�`{+?��\���#<������M����L2�#V�@�U'A`�	�Xu	V�@
�U'�A`�	�Xu��c`��1@ � �$B�1@$� �dB�	W�@@'� �tB�	1@'� �tB�	1@'� �p��tB�	1@'� �tB�	1@'� �tB�	W�@D'� �tB�	1@'� �tB�	1@'� �p��tB�	1@'� �tB�	1@'� �tB�	W�@B'� �tB�	1@'� �tB�	1@'� �p��tB�	1@'� �tB�	1@'� �tB�	W�@F'� �tB�	1@'� �tB�	1@'� �p��tB�	1@'� �tB�	1@'� �tB�	Wހ] ���'�����'�����'�����'�����PA`(� 0C�!��bC1����PA`(� (� (� (�+� ���F��_�٫qY�g���s:��<s�d�D��B���܁e�����i�Ͼ.�~�:�c�����e�y;�}�ewp��~����'M����Ɂ����	���ܳ�1�bN��٣=�l՞!{�b�4s����MY�:�tg��8���>o�t��a�#τv�,m6����֎g'��ݫ��k����O���������O������;.;;�������#gT����\s�����z���B�{��E���׋Y��g��L�噡�U1��խ��}ieG�����g��e�I�ݨ��mF޵z����D�Sǘ�K�D�}��M(H헳Ň[��娰G�/qS�ß�{�{W��������xck�q�s|\;��͗��h��3|���n�6sx�Ϝ������gph3�g�����?��6sx�Ϧ�����Sjph3�g���������@�ă�@�ă�@�ă�@�ă�@�ă�@�ă�@�ă�@�ă�@�ă��(�$��$��$��$��$��$��$��$��$��$�_ԣO�A{�O�A{�O�A{�O�A{�O�A{�O�A{�O�A{�O�A{�O�A{�O�A�s}�}�}�}�}�}�}�}�}�}����x��x��x��x��x��x��x��x��x��x���}�}�}�}�}�}�}�}�}�}�CJ�ă�@�ă�@�ă�@�ă�@�ă�@�ă�@�ă�@�ă�@�ă�@�ă��'�=�'�=�'�=�'�=�'�=�'�=�'�=�'�=�'�=�'�<��I<h�I<h�I<h�I<h�I<h�I<h�I<h�I<h�I<h�I<(�2� �������h�j\V���z~Ɯ��'λQ�>��[�A�I5�9-ggݪ��.����D��B'�Oy&tϏߋ����1��vn/K��U=-Ϯ#���ʥlo�G����tk3����i���xv�U����$X��#<�r�ؕ�>���{�>&]lj�u?{4j���5d������������t2)?����y�e�s�Yl]uk{V܍�eQ�g�5���Im�|��`q������4���oٝ�5��^������U�|��������O>���o{?�o��]��d�'A��t�|�eGxr����ߘ��]��A �  A�  A�  A�  ��Q�C3D��!
�PQ �l�1tC�!�@�bH�(C;D��CH@;D��CH �  @;D��CH@;D��CH@;D��CH@;D���CHH;D���CHH;D���CHH;D���CHH;D���CHH;D�D�CHD;D�D�CHD;D�D�CHD;D�D�CHD;D�D�CHD;D�ĴCHL;D�ĴCHL;D�ĴCHL;D�ĴCHL;D�ĴCHL;D�$�CHB;D�$�CHB;D�$�CHB;D�$�CHB;D�$�CHB;D���CHJ;D���CHJ;D���CHJ;D���CHJ;D���CHJ;D�d�CHF;D�d�CHF;D�d�CHF;D�d�CHF;D�d�CHF;D��CHN;D��CHN;D��CHN;D��CHN;D��CHN;D�4�`eı�܈q��b4{5.��lt=?kN���U1�ҭ���/�����Ge�>l�?���g�N��F]��n�`�j�N�f�ńvh��D^:�g_��^�����/��k��e��1�������.3W���mwc�ht�}�|�ǿ���G_v�'G�c;���GN�/�n�+�vϝw��Ǥ�y���g��ط������}#�m�i�\�%����Ֆ�2yx�e�L�1�f�fMo1h9[����g�����^��z���?�1{;w���׷���w��p�s~�S,�u���5mլ߿�2����W[f}��~�"ˬ/l�$s�{m�ଜ�Ӊ\��+;m5r����O�b��x�F^j��A�D��	��[��n>��x�9��A�jrs}���Y8�����ɧ�/;'�]�����}��Ѿ��zߐ}[�oľ��7f�Vo��Mٷ��f���}s�m���3p��`�|`V��a�|`"V��b�|`2V��c�|`BV��d�{�����)Y-����)Y-����)Y-����)Y-����)Y�8�d�s`�;͍��f�;&u�gԭ'n���ٝv�G���~��{W��['5K�W���Q���뙴���'�[����o�oҎ��;���n�q2��}����U��V~���|�eGxro��y��?0���d�G�:��N ����!���:��N ����!�������b�@�
!H��b�H�*!Ȅ�����N�:!��b�N�:!��b�N�:����b�N�:!��b�N�:!������N�:!��b�N�:!��b�N�:�����b�N�:!��b�N�:!������N�:!��b�N�:!��b�N�:��H��b�N�:!��b�N�:!������N�:!��b�N�:!��b�N�:�����b�N�:!��b�N�:!�����@@)�O*�O+�O,�O-�O.�O/�O0�O1����PA`(� 0C�!��bC1����PAPAPAPWA9͍g�,F�W㲪�F��3�t�?y��ɚ���v���/�ߋ��Ӯ�}]8��#uj���=�"�v�9�����:<��rc��O2���/;Ǒ����Ϲg�c�Ŝ�_��G{�٪=C�lŞi�47��ۛ�zu6���q6�}ގ�	��;F�	�Y�l;����N귻W���2=��F�ۃ��ӣן�����7�w\v�'wL3����GΨ�������j������{�녬��׋X���ދ[��?��/#���      &   �  x���r�8�+W��X����_e���$J�;c[���$p�+١�Z���i��/��>X��9��u�eFI��Ŕ�i�"��c���$ �$� P��hB�;�E�ć3��T���`S깐2�K��U�Y�:���n�Z� �V���� v�a�� r#>�����b�p��l{�u�R��^�5�:�ڽ����:�X��j�O�h�A�}~uF#R����kJ��\��?��?�����.�b�LW�G�t����"S����1I5;��?ڍ]��N>݀yCf���#g�*�O�Na�e+WEIVN72�3~��)�X�ݍx!�A6TI�Ho�l�������`U�d����tA\��y\�i�����gmE�QF-'Y7A�3���J���m[~@׽�xt�F�mg�<�\����f�;}�%�<�"�i�	�5doNi*^YFt)��x���*��9��TS������ #`�T�σ�aj\��[����/+��<B��c1ڢG��������k������^�R����
⅌G<��6��Z��V[94�벤s�Y�465 S�tI]N;����ED�r��9ߋ��Ų�7������v�u�crK��yQ��ȑB��egx���v���۠�n ��m�4����}��U��K�x���i���[R�M��
�-�;j��r����������_T����y��;�`1�t�&�����E8��?�y��>O8���,�Bͫ�w��2]{�L�[�YV񭄦S����@f�d�M k�%E�T#�kF]�Vؿ%Ҵ���hs%�v�y��Û�8�����b��E8.ҵ7!Ҳ�^MSp�Z���0q���[��V�ni�X)������5+��R<���+�L�뛛�S�D�it�����И�f��]%��~1Ȣ�A�o��5egS�2'`w�>�vᜈ�����P´ԑ�+ �q�[�'�d���(��N����$���b�E�����^���;���s� z�F�ث(��}-�9͊2*�Usd�E�Մ:5�$n�Ȇ_��.�����{�v�Z�L�z��?�2�i�o[�b�E����o��T���Q�����(}���(}���(}�����zy�c�>F�c�>F�c����'����_�c�      '      x������ � �      (   �  x��ZMs9=ï��^&��G*%qZ^��q`f'vc.�]L�����6���I�6�;Kl]7��-�R��L���A�������fׯ�h֛?�z����������M�[.���vs�}`�1�Txdh��cE��n�HG�E��V��Z=V��.zv�>4�nut����]����-�cMM�N��wV�h�4ټ��-N_�x���mv�k�ؐ��Z�V|�.�`��7	��Ɠ6ȟ�M4���[��m�nWP�qVYY�7�Y�rj�?mo���N�>򊍕�)r��-�p/�.�����gDRc���	���8�8�n�0�����?��!\Mc��>
�d+q��8ʫ�>o��v��"�-��~����}B�j4�^��?�K޴�~�<\��:Z-o�1j*N��V��̃?�[DN�[��:W2�q�[�f�_�_���[-N�k)�m�!�#_d�ƛP���'X��O��is�K���	,Zh�%� @��vZ�o����K�s�Lr��rn��LP�������M�:H~66A�g�� ��(���:�%x��9�`��*Ĳ<US����/^��p�8hP�I"Q(i{v0g����,�jdo��Ǉ��h�x �j�"ȍ��D��|BOo�3C2Ҵ�qt\CIw���!78'T��Y�
���`r�7�zm�
��*�<���v��.�����_h���������]�8��E� OJ�l���+���6'��o_?�߾"C�4��8f+R���e/��FZ�'$��
���(��jD�(*c�����*�����V�j�}�X�:H7[(�����]v�% ����Bp�6�EE�m�qW�D�
��̆xC�_>�W��1�U�9�Ea-�.A�ق�"�:Kz�rkq�ZL��Q+`�F��&�"����"�*pKQ����%����?9?@0(�Q>����:ՋAn�p �����q9墫�f-\�G��"V(x EɄ���G�`�5��F}��S��	��M��t-b��� ��l���C!V�����K�	amc؏� X)R�W���an�U�B���b�R�?O�k��d��s�������U{%, u2r��J����\������.^�V���52��j	:��������v�m��K��C�ب�8�բ%��*Xϗ� �qX*�1���Yؓ�r��vs%��M�܈��70�������^BS�(�U=S�:���|�OOV�vq��.��K��w;R��c�{i\E�L�����!�}�}��m_v����z���G(yG�[!�衚�/���T��FX' GL�2D��Ӡh� 6���yѭ6_��"� ��؂իH��o���v��p֡���,� ���Bu����a����6�9�7+�Z���JuO�pB�@�{?rvHcO~�zWb��/�@E&pa$�"��2���m��\���T)Ვ(j[�(V=t�n����8)|��/o8��z���u�SGP��HYy���*=T�U9p�t;lpQ�K�R�q�c��u<Y2Y����$w`�q$V�p?`B�{�+�bC�յ̦��
�7)Ѵn�y'Ri�CA�U�~yȣ�t#����l��B���Z�zԉ�\��nWۋ����8� ��ɸ�Sj+����[�������s?F�Ӓ����>vc��w���<�ӚTmy'�V�J��rVU!��2u;��ԄȲU� -��iߡ�B��q';{����e�Y��
�@e�gMC���u..����Wa!�@�ĂՃK�F�W�nvW�����v��(,�^A���VS�����U�;dXNN��������V��P��������JF%6�b�.[!C��}&�5���\7_�zj��U;�v!��+�
N�l`�˕6�&�����e���.#��&+�;��ӗn?8���'!Sd�F>���U�}���@13"�aLo���W��d}��1��ᵒ�V$��CP�V�  T����b�Z�Jb�*������@75�,�~6�.w 5ώ�J^�Թ�����;���Fk�������M�����8Zo� ���#��(+5���rx��X�dR�:x��SV����D��h�:	2YO�!9u�T����7+�ش��.9@��(����^h;|*�B�}b�� �O��>�F� g����SSL�F�fj�AA�rvSM.iuJU�.�%Z�vNzѓ��R��#��V�e=�[�3>)RN~y�VI��3�N/�;�(A�Bx;Uή'>'rj�i��e�V�����X�pj�_x[0�&a�)8`&�*���#�VT�j���p'JM4�zz��{]5���k�6F�˳"D��
k&�;�.z(ܑ�co�\���2�ZQ��r��� bU"�L��I)İ�OW����b��#
 �Qeu�i��:稜�NJ�lu�0�}"ypn;�`Gj��v��Ov{j����4u٘0��dbJ]@�[i̢Du�͠6&ƣO5�e��H.�ƙy�LLi"��he�+��yY|2���c;�.�$i=�=�q4qu l�c��!V�%����E-NN��ۡt4����m9���葎P��y6u̅SkU�'�2e+��p�� ��G�z$ ��R�X�]��}o�Knϣ���#�:���*��8���c�����`61�S#�qz�"��F�~rv��G&�,Ύ�)�^�W����t�ϳ@65@J2MD�����zdq�d� r���d]$}��<���y����C���=M�$�<>���#�a�1���#���<>��R�<>���#����<>���2���y�����y�{cv?Xd�G���y|�g�G��-3���#���<>���#���<.X�p9���y� (��y|�[��y�����y|�G#�7>�����      )   �  x��Zǎ�J�]����?��ܝ\ɗ�+0�D��+y����-�[�/x�� ���	Ì����ځ����fs��n�_L�_�0�ob��Ŀ���ȿ�8c���#,�DR@��~S�w"�÷�mʁ3�'�h�S:T�b�=����G�^� %G��C3%qJ���*�t���z��b(b$a�1�S:TSe����/���z6s�c��/g)����
�Q~���ि)K����yyRP�q�1�YA	v�!*����ߚ#nʃ�(_�������)*�?g9ܓ��fz#��(_���ǘ�8�Ì�Ѷ��aMFC:x	�1��Z�S:�2����RT���:�Q��
1R��B	�5\��P�p�zV8�ȩ�[�F��G�$�$*Tt��B�kT�Lg�b73 J�Yם�BeDو�1}~�E~���@i#������&攅�@��|���Ͽ� �� l�
�N�R.�E���/�����-�0D����%�n'_�rYSY��;PbF�SZ2��e�*Fc�e�3�>��#�	CDx������[�ߔ�MiΏ���y[|%��2S$�с���v����j��2SWpr�0R���J�*ã��r�]Q�l{�;p��E;���6)V:T�"����&�C�P�m��(_�FƓ�o��8T[�èk�����r��/��	-tB�	PiTD���JwpcuY���y����0WF�$
�< ��G�r�.�%"�=i�X
QMH2��WuV]=g{���� k$���T�(i�)U��܅�����7�xQ�P��X�ol���־�.u��U���O��P��(�_��9)��j~�JD:Ժ���5Ѿk�΢��ݥ�}pJ�H�1R����(��D��ɾG���M�7#�0(w��"�PN�����yXT��y[dr�?�sWJEB1�����Q;�.��BD��v�h�ATP�H<��2�G;�cQ�/�Mf]YO IHD;ҟ�Ԃ�N�Z."�C�$J>]/����/��F�x�BU��e�Z�bU`y��|�oC�kN��P�eqOPk��(��)���ɹtJ̈��JK��T۸*�7�:;<�p�
��*����x�JY����6�<�l
7��PccU�AN��
�����(�p遪�L�t����(��� ;?��D(�p(�3I7�P��"W?�Ǡ����Ֆ��+D=\{�hmN�PcT��2r�Y+o>�Qg��2	��J�Β���Kw#>����
��2@�`%�k���~�[f��A�#�����oxj��:��'�}a�)_�@�ܸP�7�*T
o�Թw^U�F�h�ƅv��J���?��^S��t��ȅ� ��J�R!#�|��gk�a'�ȅK�I	���T��@��|�e�>P"�!�p��P%"�5���J������a�����M��� 5�DG��w������t�A�%4�R*���c�2�:ӷ��ր3P�1��e!
p��`��L/79?��(�h	����^%�C����\m��Q����æ-����� O�j��6�Z3SW�[��hO���8T/	Q��<�������/��P�D;��q��z5@�w�R�շ��W(.͋x�k����7D��+�<n�M����
�/�Q���������j����9�����Bd3/�Q���r�(CT�s�r�oV[��_�о�G�΋1�m¾
�et;���<�g��C;���(�&A�g8�!
�H��E��?�/�myZ,%fd<m��;%#Q�]T&��˲�3��_��\��|R'PJ	1<F�>gZ��%��B�E;��I���o'@�B�ngQ�կ�U��zE��E;`D==��B��X�����r����݋+�|�1טi��Q�uԟ̏wR�>ŲlB�c��nN�~Q*b����?ֳ��M��P"��K�� a���[�l՟������h�P�I V�|���K1��[��dJ�����ts�@v�%�\Gsjs�����M��mO�5���˰:=D����齴�N*��}yJ��z�Ep=(��M�%��93�m�(�0��!��Q#@u��gf}+����R����A�Z$�e�B���m����g�!P"�6VR�\�}�o��?!�{Ԛ��(�@+����@�c�w^)O��}t.&K`D�],�mOB4s��繶�����(�0	E�Rw� ��Ǝ��nF��`�)�ށ���44V��(U4�Bv�oo�|`�AW�t"1�(�,���z/L���e5�΀���l��X!j��
�٦Ѽ��Y�6�5P"F��+XB�P�U֪�qkt�+�	P"ځ.��T�C��U�K�UW�ڰ�?�b$<�9K*4��̼�o�p=�!t %"�b]��q�Z���6l����=�;P"�.��~=��u����������;�D�]��Fx P�c�s�Mɟ���mYJD=����M��%�7���.M�,3y�g;(�@���^���x��s��C<���b:C�#���׸CA[�^�_�ܠ�=���%��
6	P�b=��ړGg4�/�K	���{cS���2֟��E�Og������{%���^�Q(�hlߟ�3Y]_�JD<BzVC��W��e�#�?W����5�w��p��$��-Dy��_<7�f.{�� #�a=	u�����"ǻ�j��T+���oD;�{�r5��:�(M��{�~��>v��(����tB����Z�4�}���]\@�hǵ�	��J��x=�/�aߛ���*��sD;�V��^����/nW��u珇l %����A�B��X���k=]_{�~��%f�!k�ҡ�;J}?MT��F�>�[Vʵ~�����u_��K�g��h��z\�௚uV��y��'v<�Ri�x�*s���V)u3o9��0�D�(]��*��P!co��|�]?�����<a���p�h�P�#�� �e߅�?�VF�u�6�������:���������qz�f��kj�r.��p,�9��]�B9�<�k�P�}�-P�(\�o��rx�B�����ڭZqS�nZP�>�k��l��2DY<�}��uޕ�3�	e�볽v�-�;-Ŵ�����������i�{>��_����l
XDAQ��g��~���f���l�]�	��J�2@5�%�Nf=�g�F�s~�
���^��S��UA�B�Q�����+�� ��>�k�qBã)�%BT�>��n�L^m��������l4,�Q�c�B@�bFst��WZ8nP�>�k�pr�Vf��%��i����nU��3�*0"ҁ~�j�ц�5İ�)����s�󵼜��x}�׮�t�-p5��2�oU~ϝ&��r�D��^U�J�N����K�>��~wݨs�uG�H�=�Z��	�t�D�H��\:&�j���%��\:&��ɥcr?F�\:&���ɥcr�\:&��Q:&������cr�\:&���Ō�19�2��(�1�tL�O�tL�L���1�tL.���(�K���@�1�tL.�K��bF�Ƙ�ɥcr���������_���~      *   N  x��݊7��w��7��H{n �P��Pҟ�dCwC�W_[�f��}R�'_���^ɲO?�����-=����;�sƹ������݆;�=&�6�P����c���|}��{�����_��������/�/���ׇo_^��O�y������oϟށX���N#�J���� ֌ |_�| ��W �G O� ��쀅�0�Bbm���<��?kw_�6�*	��/�:I����Or;�z:��J���H@��>	�H|8��#�o�$H�M#�D�+_%	�K�A�BKp�], �8
�i�亙���d3}����%)��8��:������:�m%��H|�� ��$���1�����v��rR�f��'�;l�$�x�7�39�(Hb�N2�ۡ�Nt�b�qM��(�$Z���$����^���4��,6��`
ɖd �$l'#{"-���Hl�=i�LA!�I$^�����7uOzҺ�u�-+3� Չ�j�é[���u2	�\����LY��<�,M$Iqb�=�"q&��$]7)�;�q4a���R; �I�n7;�$!�љlG9��2;���i؁��,g#1&I��g`��R���� #�3��go(�Tx�B�X�u�(hG�|��w�2A����xLNϬ2�N��L���h�QOsV��{�BQ�f�ySc�m6V�&?��E��|&�S�:%U,K3���i��XJ�R�8~�*K3����QI���DIҫ�[]�P�L+������"yy�e��8x��J�}�$��i�pБL(K����o��I͙#H�m�u�4S�6�+g5�x,K�&ꍐ�hc��C��q�^Y�@�b&9�R��Z6>t�I;q��́�F�p��̑x�%�b�����$ސ�yJڮ�GemFQL6�X%�W�2#U+k3�"���5�e��\V$ �H�2��L�ޕ��o��P��B*'�H��}�pv7F�S4/����Ft6U���L� �T4��L����7 C0���L�2r���V��������I(��B�ȏ�.H��~��n/6�J�C��C?}��TC�F*	+��L�AQ��z(x��I{"eq��uH8k!�2��x�1�gC���AE���y��c//�	) Ȱ��� �;3�F�'$�(e����o�q�A�'��	4�z����{��Td8A*�Pm�[r���JA�g��
nv��
�Ӡ�4���Y[a�tY�H�� �]!?|���x�,k3�⾸#%��X�r:AzoP�74��m���x�+k3��=w* ���&�{�>�}���3��ֽ�34U��cPb��*�9]��;p�2�EG6.�6��J|����n��
�}�� �⽲E�-
�$����$�p�!��)����c(0�y6齛�qG��	���6��JۆK|��̨�Ȯ���*HTM�@^'�^��d�;f2	���J:��% �D�� 2P:X|S��<E6��Ҹ�XR9%��Ѧ>�"��J�oJE=N��3ٺ�J�HF��w+��}�(EPi�l��N�P�(��/`����E ���(�K�T� �(3 g�#v)�*���;~�hR��r�\u[��H�}�U�`� sE
��5�=w��:yM�*ݲiS?��n�>�Ϛ8��dΡt�����d]�pft����A�c%� �GێYH�w��г��EnEh�VI�b�IlP�g2	OӠQ���Z^G	�&(�3��������A���&���S�H
JDA����1r�?xӄ�nY�A����{:�9$A6��y&�P���wl�:#�$�"(�'<_�h�fB2P�f%~����;�?�6����d* tH�8�T6���o�����6�4�"[z���sC	�a(�O��A���JdA{l`�� �T�$Mg�`�-~���,�$��%�_�_M��)+3H_��4��xL�g�JA�[���51�,�&&��䚘\��u�51�&&��䚘\�kbrML���51٬�&&��䚘\�kbrML���51����51Y��ɧ51�&&��䚘N��䚘\�kbr�AoML���51�&&��䚘\�kbRCY�kbrML��ɧ51�&&��䚘\�kbrML֟kbrML�&&}����5��!      +     x����j�@�s���|���Q���Ri��P0�"�~�&u��� ��?��cE����H
��y ʈ1;�;^��ͪ�������6�o�|̶�?��tZ�`��Ԁ2R�RC�~���X�Oړ�X`@4�y� ��u��|�%��	"I�Q�OŖ�k0�Z��j0���op�q꺺ZOc��)
���P�	���K�@��a���(�N�Q@�Y]��e":�_���$�i�%��a4���~�sؖy�� ,��}      ,   �  x����n�@���)�BMgfwgw{LB!��͡r�M��8�8�^y	^���Mxl�
��jU��%�<����z�V|ߪ}d&W�2��2����6w��d���q������ae��������r�	��ܾk��  :wFF��@� �*����ǫFs[�)0�p('��p%a��8�V�a~8L%a��1�sa��t��Lf�M��a&�V�8̣���p'a���1Kl�S�a6���.���\z�˥]�&�G�z8�]��I�gt�:�%]����2���h�s��갫�b#/���|��J�,����?�Ԁ<��}Q}y^C�@����8�}�zmn�w�&zL��4��-w�'Ur�[h���iO���y�� ��_���I~�<�_<
$�uu�c}O�̱S� d������`�1@� t�1@� }r��N'��q��      -   o  x����R�8 ���O٥}�a��	[���*J��`p��,p�癧�'�	81�E��*r�d�O�/�̹���d<tu:]����T����F���|������n&
ES�)�΅� B�.�  >�P�@O���HD�`����@��y�}
^*��6�����������j��{|4헩�x�BSj�X"#! ��[x`D�2B6xЅ8�/ �S�C5OnZ�x�[����Y�O�g����PK( $���s� ����y�\"�h̓�0�K��ȅ�l>�H����mC
���;Qe�@���hɱ�Z9''��r�e�	� lL�Z*�rF�s������vlZ��k~�6�'�1�:�k���~؅7 ��]]���L�{�^���R�ڥ?�"NnF��x6�2х���+o��i�ƅ����>��-�lFy�6���^zr1Ѷ7��1V���5@!�po��M��r��8r����07f�����\٘�gkA���a���#�k�����(B&���)���.F*M_*z��A�7ox��Sx+n~9��?��]�z�ț\��وl�c�C���b��6#%�ыe����f��a���M�c3���n�m��o2R��KEk�~�ne����܄���ews�)���H곫��WڅU�CӻW�W+n�/�m���<�^�N��N��(u-�N=!%�5S�@.�,�}�T11�g��j��`�a����`�bK�'��5�>�SςWh���,͆62So�Se���������&3�Z|HBQ�x��)!��؁4�(���g�7̳y5�a�E���ER޿靦�ϚPq����v��}c땁E��O%�^�Q��Ry��Gv��i�쨾R� �}j�W�����B +���@�`��f�,��m\�W��������ݫ���#X�I�f� �0{����	�(̲z��ϮύE���}�v��`�����[h�vk4�~	� c{�ct��;�{�{g҉��n2vO���U���<�5�tGYQ�77P<X�O��� ioe8�z!����F$�=�_2{3��.+�=�|*��k;p�O��]>��'v��.����|b�O��]>��'ޙO��;���iƾ�      .      x������ � �      /   �   x�u�;1Dk��h����rN�)(�"�q�b$�<y���N&�A�*R�(���#��5=_��h�[�0ط1�wnm��Q�&|�ΐ�P�����C"��d�{fY��'*�Ԯ��`��^�<�"�!�(��P��ˡ�0��7C��p91��%Wx      1   A   x�3�41�3�4202�50�54R00�20 "m#N77�xϼ�����x#SS�2N=Cc�=... �J�      5      x������ � �      7      x������ � �      9      x���YO#;�������?�%�C�cБ�!�?���ʮ���bK-m�;̓���5x�x�iSa������?�T��J�(���CX�/+�ӊ�:� >��T������x{pn�I}�$om�ޠ���t�=�H��
u����Z�����6g��4�Ό3�������EUH��xj?=��a"_�o�
3,�	gDY��%��OH�[5�Un|r�uw����I7�&mbbt���M�Fet]���t��Ѫ0UF|z���1�31ymo_W��GhT�-��$�+�hƶ`Ӝ��P^��`����'��dt�ǫ/�s��Q�����ߗ	2��mOx�����[5��O[6N�Z!�&�y�9"�PVe�*����<�+ݮ��BuBi�q~�D�'BH�r�ü�!6\W���{���G���ΰh�4�U���.A�6�ҹ�h�8�
V%��v�O��?�m-�+�%6��3<W	������<� ž�pLUf��{N�� ���S�&<
��32�	�V���L�x���T9�>Fҳ���br��汎����@T�&�Xi���E@T��$�8���w���鲡&Xy��E�{[$\)b��^�&�6b��9k��~���v]=n�>g�R{���]\%�[KY��zL��7����V��z�����n�*���N�b	�,1���s�*F���	�=X�jA=��f�t����\b5�����I�-��R8�6�6ʤglʅb���Ϯ?>�<��~�<g�
���q���,B牡�����å�&R"�p�����?����I��A�2��*�)���p���:�k�݇g�S}f���pܿh���e��1�d��X���4� /���?��r�ԙ.�oF�r���5���F�S�ѝ��{N���)����!�j�,z����"t� }��h���L�y"�e�ځp���/����dg��7{�0�A7ο	�2��l��f��=�G�����H��Ddhh*�>�"�An�������N���|˝���&�j�r:RUJ
tN�$Ps������0x�.��9�/�H�������"�_:���~����u9~��.a��9��E�����D���!L�C?p�U)Cxc�k��ݢ+/*��n�"�����t��Oy}#��u�u�ԯ�G/��-&�1�$�G�D3&X�k�`�*)G�S���^O�c�O"��@G��DJ�����7*4�M��m������xZ��������1�m9c�h�"46��[��k?]��Ϻ��}qe�P�6|�J#<k���!���ڞ+��vHS׋��/�"�6���Ɔ
E��ؠꢸ���7��z�x;�/cC`XLސq O�w)�6��p�P��7��З֍9�F�Ș�J'0y�$���,[���.t�<-��M=z}v��>_Uh�� /<�m�&��I��4�WU�|����C���m<}�Q6��6��Q]DӃ����k���x4�H xD׀6
����Ȝ�)�Qʂ��+OY:����_qeQ55�tQ.s+W^��\���=W�g�e�~9��J�����2'����t�;n���*�IU=���؝X���,<�H�6�6�I����#m�ށ�4�!mr�8o{>g��ķ�`��l<�l9���p$n�;r���.�wo�/yk���y8�����q䌒���<�e:�f�]%��"�|]��}���6A~�P7Q7Ѝ��ep�nY��r�.QQ�?�5���l>�qaU�T�)���N
�'ox��I1}L�ޙ�����B�$�֓��{�6�ɥ�4���,s�nۭ,|�:�}����Џ�)=:W<_'���6R�V,�����y��#���_�y�䲤"V(<P�qKt�f�G&�.q�<��B������:ۻ�>��M!��HP$p�\e�k��G��|B�ćgp�\�o�G��$��4��""�E�?�ֹ�#e���+��M����Ŭ�N'�%e�B`�������ϑ0g	��W���MZ��o?j�R���EDb�����ё0g^�CǦ�B�ո�̝o����U	�1:b����t���2�w���NӜ�z��W���Z��{Q�4�Q:�����]�iH;�FoJ������I��;���9!��x"�y��:Dc�,U��S��+��y]P�4����O����
�14⊈��a���!��0�٪$�'�wc����bٌ��4���a�y<E�cXѨ��
3f�k3��̒���+�d�&"�")�Νَ��G������~W������Ը�%G��XCkn3Yn2m���ꨵ�}������{�n�4P�%J�� �n(:��]Z�݄h>��t�'w��/M_G��Țtq���d�|8<[����m�����i�v/S5�Q.#������$�p^�sv�A��)�I��}�X�J!��a	2�j�;U���Cй���
���Qk�];�����s/�P^sQ4�*��	�����\�)K��,l;�v�n�[�|1��0ڃ@q�eDФ�y@@s2�����Ǟ������s���74MF4M��CmV�P�y�u@w�P�Z�����g��x�>:�k.#�:Jo����%�2�l���ߝ_y�FGfu�i?�_����&#�&��:37�z�m����v~��g��]���"����!���Z�	+B�$��)kP(�vM�yL�b}qY��G�)��maa&;w�z#h@wYF���&�~����a��� q�@���T��it�bF�7��e������F����xW�v�Sg\�`�*�:|#x������Yx��Q��!"KT�S"g���d��"�!�xd ��f�8=}��,�z�D\�<05�=�NȀNқ���k�Cϻ���fY_ a���s��	��|�u��:���܅m]���2�����{�
׉�.Z��Τ���\m������}ҕ�2�m�dQq]�XC/WZg��b�������]̄ebk���o�^����z��N��ԩ��7��)�cG`��i�OF��l�N���ګ���t�B��Թ��e���̥�Xw���'�_!ꁑ���9$̐���~��/�����G�Ti,�������i�ʮ�,dNGd��DX����9��IQ���#g#/����h&�Ѵ]h��h����Lߑ �k�nn��f���]ѳክ�%�V
�#
G��ElnlP8)|��C�m��ג g�㯻����% ���&��S�#e@��ܞ�6oKO�����n?��TN�R�:"oT&Z���uC�d.oD���]l����n�z����J�U����������~�~#u wvQȥ6w�~.uֹ��}E�LH��H5n I�q�H�u��us���������.�FS�K�#��.�h�+⾑�@'��'ޅͥ w���CuLD�(*TXz�iF�7�h�f#Bu����b�W�]�e�"�6�6F	3��.(B�Q� (Y
���;o����vŢ��0�ac,a�R��¦X������^����u1���Dtd��H�7:?d�ܰ+��������f�U�^g�"HdùE� ����4�E
7�����*	N��&=6��V�i��Y�#.H�fx�ԀQ7eGrtZ�U0�E��/�Y��aU��n8~A".h"��y�/跉��F]�-�(m}�.� z������q+��H�Bge>������*TH_3�ߟ�q�h����O���Db�@�̤R�0)�u>՘�f1���kt�O{�k�7�+.�<�� �8"�Ƀ7��-���.�	�M��I�O'_�i�"�@G��P	�ax#Wǝ�85�����G�?ќ�N�t74	r$��p���E��"�S7N΂k���~�-�5��|��)���D��*+Gl�d�#Ҧ����i�k�[o�������C�݈D�\W��T����m�����f������v���W�]�h�r��,�q}N����ddJ�(�H/�]�Z(�^��3ݝY��>ryZ�z\qh��,gJ�K���? �  ��Cw-�*�!����y{|�;�
S��"t];��4��I\"WTгY�ޏsW���ث��s�꫿����x��b�7�$ӫ@wSKX��2���AVx�q;���8ySGw�%ḁ6�ub.�$4���˔����7_
�J�N��d~���/E鄝{�p ��u��L��7p�+D��Ѕ!�+5�y�{�G�*����	{ ��u�4C���L�m=�H��J�vg��;~�6�HУd��>+\X�U�Aտ�Ta��r����{1�5��V��F���j`�Hѿ�����t{~��f�G�m��Hq6�`Z����gy���oa���[;�is��v���d�Dګ�͝r�����,�=�P�U0��z�]_U��{7����#���L1�k��Ȣ(5���w�Vf�ΛM/i"��M�}s\�dC4ㇹ��|�wCj�i�-<{:?z����r1��Mw]������,,
�d$d��Ͻ�"�6_7��Yic��f��u��y�!��w�4<�'O�^IB�W���`wmC��b
6����0H�T**$��@O�ߘ��t��u>���R"���LQ�Κp������++?��^#�^_���}����mc�f]��B i�WҊ��G�+v�fB/qIC��s����2�����rE���>��2�C��8ܓ�xe��x��§H��wu6n��?���r�.!�ݰ��`��jv|����ڥ�w�?��C�����a��u���5��]e�ي��h����ԥ��V��������P�8�2�%%|�t@an �}�f�*)�Mg���1[�����=7�I��xcL2���@�N��k&���-��w@�M��X�5�.��$7�B����ɖD��Xl$)\$�dE��sY�����iT0�T���3>��-Y���N'�cPW����ap8�
t������7�@n����ꤢ j�V��D�#No�1�b���Z�i+L�p�����y#�tq~��_�2�ƪ�#7�	�F�����l�D����������>�%�
�97�	�	A�7 #�n�^���5l��t:�y)�#�q� n��,�Z��Rp~�~����y�V������=�Q�ҹ��I`��X���{��K����wέ����O7��v��QtHF!K�&�'a��N�p��v]��f�}~~���әl��%��Z����I�M8W*�B�c�	�ԍ<Z���s���ˉ��x���i�����ĳ�c8����MLXǴ6��Cӣ:���eY���m�O��B�/��oF� N���p�������A�]a*�8��6	��B������iN
�h��o����ٕ{�^��و�q�;3�Y_5�Ѭ �x�E����KL'�R8�Sa����#���0��lw{j
a�]��������bQn����a�¸�#�J�m��jO]�_U�l.����y3"l�py9f�e�枟�* ��v��|�a6Zp7jZ軯Ǐ^c����UoJ�9 n��
�^�(�y6p�Px�4u�p6f���5h�wc��q�{�-���*�9��㹛�^���tHP6�֣�?ݫz���wS��"��=A!�l�nh�68-��ף���,7}�1.�#�����V�`V{iLx�B���J
�FZ�A�+�̼s��E�H����򔬥$��;#�N�|��"V#�yk�+M"�;�÷���JN9�y�'�|���}^�aZk:{�^)��s�<����Ȭ��ߋ���)�p������YLI����)\:L�;�����v�׊JH�����I� �x~�7ƃ�o�9/Ĕis������pY�]�	r��@���lϙ{�D�H�����o�}�o�����-w��h=�T~����s>�N3��m�=�����=�W�^f�nZ$��#ާ�i�H�W�" ��a>�mǭ5��ݫ��-�D^�e6�{���)�&7_>����_u����{��aQ	'�d׷12�y��g�*�N�^KsQ�Q4��Ey�F�-���!���2'��-G?��%7e����2���M����?K�}       ;   \   x�]��	�0 �s2E'?�b� �ڔ4E�^�����p�9�C̝`�	�j�)�ҹ�C��I�-j�@�EL� �&�[I����4ӿ�"��;$/      >      x�͝YS#;����O��{��}Ꭽ�����	��b��ӿRe�mMSe��0'��>Ӯ���󤲔Y�w���lc���l�v;��͗n�9��h?\w��z�m?�.<7>�w3L����|F)1�f��3�ޞ�k��n����_����/,�����v���c?����,�sR�1��Z"��?�f�?�_�͛�C��u����u۱6���ڗ���l��s=;���/�}���9�q���&3�p���_�g���O)7�_�?�cѳ��9�������֛+�9b���9<WN�y�^N7�ޢ�m2�`���lؤ�c��[ ���b\`�;IR�q�A�$M��]�,5�X 0Ol�� ,R�q� ���A�%���n\���u2���q�N����fG�u;n'����؝Jnwt\�3�w*���qπ��G�u<���;��X�Jnyt\�3�y*�籾�n
d�}(���헋�Csvˁ� (X��?�3;#��˦8XXX����?�*x��PYMT07e2��z�\M���&*ؙ&PeMT�1M3����``�e@�5Q��4πjj��ei���D��2��z�������Wg���Uܵ�m;|�G7���R�qi0.Nf�[k�����dO[�������i����K6�Mrb�'ɰ�eGP׼�������y)þ�a����Ě!�v���1�0���p�x��H��G=;�x �0���o����]��Z_}|[��;��p!�"0��,WN�Ff����s�������iĢ��鑧����grzVп�������������u�������f_:�;��p�v"�"6E!j�K�?�p�Eg���_��۷W}����rs��c��LAG��G6z�Z}��������g�u�����9;���|@Gw������W��]
ёWE=��a|�|�������ݷ�QA�Q1����ƝS�浧�h>|���n E��3�Y�&?��0�g�rWݜ�!�;��ͧ��T���x���r��l�/6��{7Ǜ�7��.$��T�1>'�OΕ��kOT����ʙrJ[�R %�Bi*Rb�YJV�R%�BI�~�(EJ]�^Z��Y(y�{��R���IUL�:��JI��d��珪��nc�(��fFz���wZ��>��������mOඋ�b\m//����-�(;Yo<,ݭ�����2��]P�����Ѵ���1y!jYj^1&/D�.�O�+{����8q:� �wa�tN;��+H7n?�Kʰ������k��:���?=)�N�TR�TW�Pt}m���j7����Rĝ^���� j݆�E	����3S"~����;"q�< 'n)1HZ�d:�X��}ڋ�������p�"Xnf���d�~]���y�.n���W�c��q������&�w6;��X�c�>}����9:�ߠwq�@�|N�G/�����˥�k{~�o����9;)lx�,\�����m?XrY�k�
��y!���x���\��8�������i�pci�V�.�Z�,��o� �yk�Wkqa/���Muw���
>�qB���J���[��ퟓ�hO�j��� �.�Gnj��p[�wO�dl�o�i�����Aҥ����`=��_э=�@w�l��i�'�o���x�U������^���f�O	���F��.�7K�x���s��:�l���`\���q� ��zg{����̈́�`Y�g �_5��U\�zS#���X�������n�;[w��``T�歟^04�R��χ /xթy��C�L��Լ>�]@����},�յ�g%H�z��>+���� ���$���L�����}�G��B/V�,���l���)���n�Jxdo.����:z��n�����,������޻�����~��=)1no#�NQU�����/��L�	�V U\����C
4x3�����y7�)����y���%�'Bю�9e#[Vs����7�}qm�:/8өyYu	�&a�Ŝq��n���r6��l���-7[�,�����Y[���nO%�Hy��})e6�}���r����g{RpRN���u����rs��1[m0Rt )P�k��<����~�o^�?�"�~�dPRQ�z�ԁ}��].�=}[1�ks���Tp/.2�V/ZT0..3�V/ZT�,�2�V/ZT�+�3�V/Z*P���&j��%@��6j��%@�$j��%@�4j��%@�,�����8�J������d��Y����͝f����c�g	Q`�� ��������Ud�w���λ��19�3_
`�����b��P��&Tj^6&/��Щy�x�ЫC�61��W8^�3����z�ݞ���y���#��oҬ~�}9���R�`��l�]�!_W�[̓_�D�C���ꡇo� ��.���h�':��v_
�t�-%$7;����I�7��ߠ׎�ΑK)B􍒁,ٗߧz�>�\���C�� �=Q4���#�w����a#b9R�ݹ'��� ��H�v~�.�����.LM�1(��򼹻~�L]���Mzgy�e6Wۥt��q��f�"/��uiR�1y�٤M����s_b�;��V�G�m�M{]:w��r��+ߙ3_X<��67���[}���έ�8݊�[;y[���)��w�d�/	�VBy3����th��^��i;Z�v,Y���)��w��E�Φdj�1�/؜R�y�{��_��;�rƦH `P��Rq��jߞ~����P�T�4e2�V/���)h�>.�Zje�x1��?[��U�
Rp1M2��z�`_�f ��H��4�@*ꑂei���z!���J����ty��c�m���/u�^�a.W�JK��?�0Wv������'��F<M�G>�U�:mb8׎�J{�
�����Qg��CFLL���fFCK���Z����Ki���Ƿ��l��8��̬x��Ό��}�x����};ZS��GQ^����Iy�8�\�����w���/�[�&���cM.x��O���vf���Q g���2�K*b3�ox ���`(�7��7��S�MC��m�
�fT�:8�<��ݽf�nW�)8�oz����"�f�7IN�둂w��TT&đ�k�#*tP��5J��������M�7'U�R�+�Z$9i�-cA
NeY��[Ƃ<ʷINZ}�X��9��"�I�֧�f����MET��� �V{gaC^G3�[����7IO;N�j��;���� ��{���K����~�x@�o���8�`�2 ��No��e<Ț1��|�7m��VNK�D��'*�����>
�½�o"���V�@8*h�!}Ðp�Y���(���9��-���1`�W���y�K��.����jģ�E�K ���(��)�b����^�(�G24���f�e;�{~|�<� � *{�C�CI?����V�,��zD��@�� 䲽<��<�H��p5��2 �����h=��k=*4搾��C�a�kO������;���U8X
�^�9z_���|逇9k=-F���k҂��."�iYmZ04�>$=�8�g�t��h^]:l����'���[�Չ5ֵ`�Ak}K=RIH�|�������h�,�d����I�;�>�:`�^�PD�$=7>?�K�p����K'�K�{���PQJ�b�W^��E�ШB�F%����r���R3_�|q���2^�-kA�R�s�֊���(�^+:J����T���gk�Q_e_��9�D���J���r��[���^|�&�3�>`6�;�!�'�y��)ba�7�h���oXb���d�W��j�-�I��S�����:|S_�K�:{G����hG�k�-x�{|c���c=.x�z|琴�|^<A��'��w���x���n���mm�0�H��|�:Z�h�J����\�lTC�L�-���bBGK6>�6K}�6۟��(m3�`f�qHz�z���Qh���S��7@��[:0�</��8ށ����c�qHz\Vl�wI���ァq�W��?�mD�����`g��Hz\U�I�V�pu}\�4�R$=�����ۊ�ǵ�    q��|k�6A���<�V�נ��o������Y���;��� o)� �[��v?Z9y�TlA�曍����8.h��|������޿���6GC?	��[������5Uj�r��z�7�ab�[��:�I�����a���R����Ź�Qi�~K��A	ߥW��5���R^�I��E��Uo)w�``���g�VwJ/��`��g�oO�hUMZ�/�O$=-�M�廉���i��|o����6-8�o*��V֦��mD�ӪZ��тs��!6����n��`������&��U{L��C�Cl�nm�c�Q��ܯ�v��Ѩz_Ռ���)���tݟ��Rm�����kG=�7�ɝ���7���p�����l�F|�S3�[����"v�I��$��g�f,�ڏ�^�7�U$-o���Ҏ�������U�����׽������v��@c�Dl�������ξ��ڨ��A��7B�_$,m�e�Ɵ�}ه|�������	+x��b��H�Zk7���ID�mˠm�/ŕVD�q��t���K��ލ�'���MERӳ��t�������9I�O}B�	_�$�����?��������;��zUj�8��|�7��r��$rk��v�#�\M���!���~#v����vUz��v7z��1ڛ%k�s��绎������rh:Q�d'�c�﯏���mz���g)�>W#�3c�;�OV*�j�?���h�Rv��#���-X��h�h���?C�	�f�"#���%^�r4/��]���FN;/����e�tat#�~�tVN��W��ʵ�A��5Q u�0�K�����Bk%v�v���O_�������Ս����>/��^�Q$j���0��s�����Aȭ����h�YcL�@�	@S�ѣ���RoEr�w�о�(�Ռ�8'���h�rB0����K{ZKK��W�I+OX��q�ٟu$#��~�X:f�>Nwn��Y�D��+tq(����:������[��[�7�oQsƧwV}�pN5�'����'��WKv!����`�#�� =�����~��L�<"�s���<bP� �~-�V�����;�����l�h����\+�4m���|���Z�`��`�9�Ǫ�����ҶL��t�''u"���U��7@�۸듊��� b�஡�lyXsx׏�~ۿ�x��.����ت�hP�~Q��vN�v>#�����_��q|���45o����>�0����)xm��;_/��ݩ߻��xG�k��c�rRV�z;��yz�qvnMh��Sӊ�&��))5$�W��Gk�����t���B	�w�,�.��6Ϛ;�gU&uA8�t���o!v�[����ϫ'���LNꐰ��3Ył�/�\�`v�w�^'�s��I]H�|��V�X������Y7Z)DeRB��JT��!ҷ����.��x0ۋ�vaUժ �@J3�V�Ղ� )�@���Ư�tl�)9�B��[��N��5n�ޏXVR7^qe�s���)6��ݜ��Uל���F4U��?���+S��O��e��R��O�����S��O���kR��1x]�"$�z+��5�Kg�[ϭۻN���~oD��FIj�1��Q���Q��w��r�`n����ԅ]b���b�֥oWϥ^���G��!h���Op%�OƄޱ�H�J/7w�g����.L� �@��"a�6�û��yY�}�:�����V���W~A���Ul�I���u�B������m�j�t��[\�G��nmi��Vg����huC/����&�ylކ�v���\,.�ܗ�C'����Kog8<Y}���x���;����yN3H�j.����k<����M��nw�����]��1����B��G@�
��U|�`����z��������y���:$X��rZHV�S��XbH^��ǯi!EH�|����u a���E�RՁ3�M��B�}�����#���O]z����������h�ٙ����t�?�'�g�R���T�����4l�k�GT��<'5H�����@��P��P�����%�%������J��N�1��%,^�N���%��.A�|z�`x	������wA��~��ȟ��j�f�S�ia�KĊ��uQL;���P�-aN�C��`9xf�"Go
r��rz��`��1O�/��w	��0ӻ��K��]�_MO������[T5=����jz�oQ��Կw	\�jz����V�[�W���궸���V��խ���-�n=��mqu��nJpy����ٜ��W�/�_�ݵ>��l&C���x��p\Ѿ#���Z�U�>��6~���{�{��L�{w�N��H��>Uӟʏ��(���|��F���ܣ6
�?���Q����MQ�9���+�LceR\�fK���Ә�'���$�8��4f9�in�1�)�s;�y�p��i�s���NŁp��i�s�d��ALȇ]S�p�>��~�4Vh/SD��B{�2�����i��^"��\�����֙�s�a2�L���@y��rb����a91Pr����(N4se���b#
����tJ3?������:�������@%���_FYf)�me��wX�eS܌Q�YMq�FYf9�-e��w���~h�z�rǥ��,���v���z�۹��z�����z����z�q�����z�7r�^x���q�����z����"r��?D���c�!2���9����@=��T����z*POE���̬��Tf�S�z*3�@=���T����z*POef=���2��J�S�YO%�̬��Tf�S�z�r?j@=U��T����z*QOUf=���*��J�S�YO�ʬ�
�Te�S�z�2�B=U��T����z�POu�g���:��*�S�YO�ά�
�Tg�S�z�3�F=ՙ�T����z�QOuf=ը�&��j�S�YO5��}��d�S���&��b65�����ɬ�X�MMf=�zoj2�)�R�YO�2���z����f�S�1�6��b!2��O����z�%��f�S�{�6��bq4���+��ͬ�XfMmf=�ZlF2�)l3�YO�����z��ߌd�S�g$�qm���k�}`�P�}b�0�}d�p�}f������\ ���6AY�]��� $��m���׊Hne�R���5{-ð��0XòW�`9�^��0,{=İ�1XòW�`I�^�51,wMŢ��(�bU�]C�,��*������w?�����k\��6�xlQ�2���G-�R�[�ZB��^E�w����������"".aѤ5�碯MWc|�՜������T��9Ag���?�/�� $���^��4�����m������r�\煯�Ir=k|�M��a���i�\�_7��z����$����|!L��T
�۝�T�ۅ�T��m�T"��U�T*��=�T2��%�T:���TB����TJ����TJ����d�*�L�4X��d*���|�R)��3�Ji�О�TJ���J�4ؠ��TJ�M�J�4�Ȏ�TJ�]�J�4����d�&T�Ji��ө�;0�Ji�Eө�{0�Ji�� ө��0�Ji�� ө��0�Ji�1 ө�; 0�,C�JcR)��3�Ji�x��TJ�U�̤R,�g&��`�=3���I�4XI�L*���yfR)��3�Ji���d�`T�Ji����TJ���̦R,Tg6��`E:���KϙM�4Xc�l*��brfS)V�3�Ji�<��TJ�u��${�����+�9I�4X��I*��ZmNR)es�Ji����TJ�e֜�R���$��`�4���+�9M�4X
�i�Gܨ44��`q3�����9M�4X��i*���dNS) s�Ji�Ҙ�TJ�%Ŝ�R��,��`�0g����9Kv����R���,��`!/g��+v9K�4X��Y*��\�R)�r�Ji����TJ�峜�R���<��`A�?��肨4<��=�>m�����g�y��_ד�/'�j��Ga)z�O��QG�^���|������>��͛��ֶV�å�?�wW����D� l��9&�$�?�f��i���;��?� n�a�G�'���Ǘ� �  �yy��n�;�뗟/�g���{����l�7>;��������B�Dx���ɅA�g�߽8�����MB�&v�����Qp�D��I�M����E�a��,[}����{?[E6�aˊ�6�a��-��M�a��8l6%sD��mXoF�ް'��2V	c���JIx��<VɆ�Z��٤�F�I��`ئ�ixج�cFY�?_3��[�X��`�ak��8쐎Q:�Yi����m1��L<�8:VQ�h)Ye�:4l6�c���������Q�maش�C�M�yY���$g�1��7�آaf�a����n����%i�k�˒�O8pgq�7`M��̍#0p3�x��k%�����X�	&7Dg�btƂ�f�Diy����|�)�H�<��A}�5�3�53'K\�㳯���m��6����b�Ƃ���Ԩ}��w,N�U^�T�xpaہ���p��y��t�<Һ� �T�xh]32'Li�}���MM�aGZ����A�jF��۰���CΉ�iQC�f+Fi<��q��,��&D~�x��&��@�f+�i"�s�f�����8[0���4����mJ��r����9L<n�l�0M��܍��Q�v/<�ܼD��fm��L��p��m�R�#I$Qΐ`�qCpf+g"�"f���k��ݏ�&w�T)�f�bl&����Xizˑ`%��Ƕa�Y[1HA�f���������Ƕ�N�ڊQ��6�ť��h�Nǵ�Z�ڊQ�mAܰY�	P(� �m:E��V-R1N�A�sT�F�_�_3�D�+�H�mq�54n1�I1�ds�2��c7!#5�p1�mYچ�l�}2�scM7!C�49ha_�7�B�\��4�-'�b�"��&=l4f�?���dM�wB*-*�s�-�y�����IG��l°؞��a�
�����$~ q��8b�5��T[TP��-kZ?&������IGI�5��b䢂=GJ#_��5g?�������D�ђ������i/^Q�o`�B+�+*�#����GK���	TgG�� �����9^Ҵ҃���E���ĩD��
�K�sK:�czN����fLD�1�ư9�C6�13���ޫ7�~�:����6� �bȦ�>f�8��"�d]Ǳ1lBhŐMm��I��o\����<��a�B+Fl:�gv��P`����8v��S��頝�q��i�$�̤�dg�ъ�ڙH��xԑG�3�X��M��vPܾ��J���㍳�^6�U��LȾ��iL�����&�kc+�*j&tTˍw P럙�K)��E��9��=}���P���F�O/N�j1ql.DX�Ḧ́��n��S��x�7�'�0'�������P��ǫ�j[��ĉS��a4�S�������R'>�O�U��L(>q�-������&��8a	v�"�b<fBa	g�lK[�)D%&NT������PT������ySEJA��#�T+����٠_�9Q>�1rd��hm$�Ƹ�W��lh���)g�
�w�$�����d�W��lЫ�x�r(���ϜN�:�qL�^1<�A-s�lڴ�<�U}����Qu��Fx����MTx�j�p�6���8چ����)�{Ɂ��G��M:���#�@�W�	���x��.���&8Fl�ZĦHp&쌏T3[���G0��1t��B7E�~&�dy�#g�l�'b=��Ǎ����`�:Wg�Ff�w�X'xT��u�`HD��M�������Ӓ�O>��lʰ�"�"7E�F��Xy��g�7�h���ą���j��
��O����j�Ac�̚�T���%����`��x�h`�1�,N �.����`)?���� ����'��#��c�M"*�n�j~?r��E�W�I���X�������0rA�k�ii�<��c�Q"*�n4�3��b#Ai�6p���=��������`c��GR0�F+��L<n�D��-غB���f8�/�&�\��_����@ ��      ?   }   x�s�L+IN,JQxԴ�Ȕ����Д����H��\��T��������@������\�������X�����hXNf��wy�O��Si�i�e��kn�E@q�Yg��%���x���\1z\\\ �!�      <   �  x���[R�I���U��=1aue]��x�0�� 6�FGL ��r��8��M�.f'�����R�%Ѽz��9��@��?Iu֟v�I�~�J>5����+"���ӻߘ���L
)��ݗ&�36'<W`A���#�	P������a����\U���Y�T}-$&���qi�@0؃_�����F|�� �8U��q~A��y�w���c�|�+1�mT 6�Q��/p�<�"��!*���Q���@5�}�B�.���Ј�����d��Lu�nA����QB���~�+��*��j��W#���Se����Z5��
T���<���]���l,+��G-�ǩR�P5���*���mV����R��م�����{_˽���מ�(e*"A�U����]��Me�R8}b�;��bΨM[���ѡP*�T���Q�e��я��@uW�U;�O'���ף��1z�_���Z�JU@m�Y�2����q�j��ǃ�-�1�Be7��:fp��ܾԩ��9�y�kQm�F��pK?x�a�ԉ�eL�N��d��K��\�#�4���8X�j�pѡ�5��\ߞ�k+�>_};�U���q�,�XQc�h��JU�,Χ����%UC�j�z��KW��'�n;�E*t��s-@�jLU�M6�t�ҕ�J�.�&p�_^/�'�v�&W!�u'݇As��Qnt��D09!98m���U��m/O�:�V+�?_۝\��gL�r������1rC�<����s����p��.�ρ��0�U:Lm=@�Y�7.⫸pN�JU/mFEiӦ_e�u6^����ᑼ.����v�'5ys3���d�:%R0�oMe�m�J�Qg��g߉��n@>��;�^~�3��m,2��ĵ��0![%�pп�rM�BH�M =�~m��d���S���`*=F�����O�Y��      B     x���OK�0�s�)r�����ݶ�8�C��A2gR
md[��k��S,N=	�\��&�xye��U�"!�uQ9��4Vus_��/|�֘܏9
��p4���|`�lF烸�S{78�f1�q}��)kFގG͛��f�GEO*���N(t���]J������DQ��7���X�g3�z`�ٍ嬵�@@�c���Y.��W�^�'��r���+��Fs��ִۤ�%�W�x�]Vw�G��\J�mV�v��X��	�����;'I��%aN      C   �   x�uϻ�0���y�<"����,]\0�ĐRޞ�4���ӑ�*hSL�d˞B��ot���	5���C�B^@c���{|$��dr7��]�/�L��R^��C�r�>>f[�Վd��gW�)\���)d'�{>#�M7B� >$P�      E     x��Z]o7|�~� ����?�t���-ۊ )䐗�v�i��]ٰ�U�g�!-�-(��$����#�`��������~�~h���\�g�?���9�ύh��"�r�E��\kB�b��W��3{�M�~�\��nZ펔��ⴠ0�9o�J������������G��ַτk���X��-����׫��WW����l��=�?X�<��ǋhvz;b�ö��o��q3~�]s�~j���6�a��7�}R��)r����S��yx�C��!�d�/Ͳ�q[G����C�e�\��a���#EA��ً����0>M������S?5��GRɚ���\���H�jf�������a�7�O͛�\��˴Bk�e�˴JxoT����M���!qmA�B��Vr7��a�sv��U���ݴ����B[h�PV�[�.����l����m� �O��e<�މIZ{��Hv1��f�7o�n�b ��;�����ETin̂K��nv��9�pS��Q-[���!�@qPz���@QL��ET+����)5�N|���{�ּy�b��}���u��7R�<�]��!��aѭ��<	�8��hv�8�u�Z�!}��i�Q�h�֚�ư��g�f/J�^Y�%�N�ŉYv��7��f��H\͎�mu@��<��7���^���Ksz$}1Mr����|D4�S�Nw�u?��X���-�����D�I���^���<���H�K��BДO ��������	��y2 .�9��h��%<Zk�<R�X���g�'��!%�����n��{Ш� ���ی���4��Q�z�Ԝ�P(�Vk�g!���Q��0T`yZ���ƭs)r)4�(ti��^|yܮ:؄�q	�O�c��	��]ğ1�Eۜ��ͦ[��i�&�:8i���Z��`�ml��p?��6�ލ�h��xZ���(g
D���1���'푒O\\%(��[�8viS��^�8��7�ZǍ���"�	�ʛ��|��@R# ��F�8"�H��
v�o{\���~��M(�hD�Y贕��~�_�d?��u�����:�Z(K��ެ��&�a.�VV�<�5!���x���������&W?gT�Łv<l:
����� ���\�j	��s4P���5��ٺ6��	��@�OGV	�A�ʂ� ����#g���T�VKeF0�F��>P����ܙ�i���@)�{�Z/4���u7P���O��v ��-҅v �V��� I�oa�}M3��uB�,��R�G^�Ǉ�g�aA��rl��kF]0i$+��ҧn��p��]�0��ƴ�ra�p��P���ӱ7���u��j<;sw���B�S&����fխV�^cۍÅ��1f�H�Ov��~w�BBy�o7�.
B��̨��9����w}��q�[��AW#B&��kWHވZ�4ׂ]��{4#��9�LH���[xB�)�פ%65퇻ms1��Ӽ�q�ZX.��AUJ/P��~?����U�׶@B�T.��Eh|v�����5�Pz��M�LE�lcZ�{1�@Q�	eh���b�
;��ض�]��Ty<z��V('xެG�;��H`��Uc=�`}�D��Z��gW�Ay4��>gP�z	U(�)�`'#J�*[W��Լ� �	� v�]�{���IH�Zi�6�� T����s�A�jL��-�v��<	�.,����(P�$�B�eD�k��zAa���Ԝ��ص��\gG63��%�g����i�XN�Ze�Biƞr8���i#����V*���Q!=/���mO%�F�h��yɊ��=ю]�4��,n)~�̇VDE���q����r��pG.Y�� \h��H`o�~��V�zfQ#+<�/�P�m�d����o��i��Rst0��[%)C��筇qh�ѿ�����爃���%�0����D�MO���96�d����8�������AR#2i��-m�P)e����DZ�>\<�5������3N�R��Jc��A�T�^ĭՔ�[�tDo�"���+21��{�N�A�p!�r�N����MB7x=u��NU$ْB�|�E�>-���u�kTN�Ԩ�Q��]!E#������p�u$��r>�"
��X����i�h:��Ux��(�$�]��u���Dv8���0 �Vh1����Q����Rӧ�����F��`�1��ғ�f��
�;�h��C��*uQ.��i3��B]3��<xP�xS��KY�=�L�P`pV��q#��q���o�IWqҶʢ���"��N� {�#
܊�ٌD�7�@B@���ytӜ"�:�g����X/X��8����sCi����i^L��Gzn����3�=l5�<)�f��>Ί^u��\k�-�r�^w	tʧQ����j��hȤQ>;��Q�N���_�6h��d(�P7K�!�c���{���x�� ��Y��F��u&�J;��;Ts��V㸢O<�fǊ�}a�E(zr�S����i�����҄ɡ#�NkfT�I@�v�~���M8<S�E�m~�0�h�c��D��95�P�s��̨0nA����έ&�Ei�� �@���go�M��TU�z�Jz��w��n�\�`��PNouBd��J�x��]>������nدAW������|��hЋOp��>�$�[���|�+Ss[B�|�B���	&u��?��w����`L�G�Q'Ӱ�\�9Ó��Y"�6�Ã$��/\6�h��~�'�x:aZKⓟ�GTk��\���{�p[��M���8�v��-��>Q��5FA�uG�����Bv<G�0���/�"[�nX��i�fv�� �B6$o�� p���l�B��	���i�_/��&�<�h}C�jj�m�� zޚ�h���3����E�H�{ӓ���0ӾUB��8wF���2���t��N�fI'Ɗ��n���N�z+��[*x�����ҩ{M�.��A�. xu��Y>G)(es�=|\?��Pj�����V�P�"*�L��u�}������+5q����!d���Pԇ勨f���&��5��h���#G�p�T#O�7�GGG�t/�	      F      x�u�Ys㸒���~żO4�rߴY+m-�%9&bB�X�%Y����	vt���:�S4�_"�����������U�~��hp����c���C�������JK�����K�T��o!���n���0���K XD�2�t�:U-���������v����i"��@����F���S�0<�ְ6�(e�u�;���π�/����`��H[ʸXC5aܨ�Z[kk�Y��W�yxe���>�6�LZR`�T�2c�[Hc�-�hO>7��<&��?��H+,.�S-�Bd���2�^�V{_]�����i���&�S�k2kk�,�b^=���ٺ</U0Xs�d�SC��YXsXg��}�b�^�����&�ET8�S�g3g$�p�2/���^��M��H�51� �S�R����(R�ظ,�{��~�9"û[;�s�I�)#�5ѓ�:�{U��HF�[��f>A��"�Y�\��9i�o�vu�֑�V��3DF���� �e$vN�E��ݤ�L�r غ�X����{0 J�ȸw��_��(޴��>��H"?
�G�j�2k���"�(���E����E�B�ɥE���!����̃x��ؼ���t�l�ƀ�;C5������o_��lQ��R�r�A"���
�Tf8d~8�E|^lJ������2���s**�p��!�;���K4�^�f����i�h��S�ȭ����z���g��yٗ�{ 5�pJ�*
"ͬ����x�r�D������8��KTO��Jxkp��!�T����ˣ�qXjF"<r�L��d��PC�rcَ��ai�\��Z��#U%�Y�@����sx�����;���<�Y�-6�k��,�)(Ԡ��ܸ��=,Ͽn�E�7ʪc�j�m-��C��� ��������4@���&�x�I���\�֭��PY�zU�v-�<\�����D������y#�*P=Z"�
Wک�ֲ�� Q�D��Ȇ�����~^Y��*�
�US�H��A"�I�_��d�9���6�tD��੝���Ǎ{� Q�$��F�d���Ny0�jƳ
V9ɪ�6�5�D��H_���:���L �FR+l{a�T5��3�5�E��H�t��__�����	V�G8CR�w�ڸ�E�"���{��nмL�4bH^�T��2k�SE#U��Ӫ�<��,"��p�KE��(�,��Ed���ݞ���nwQˑ��a2��(
���Kr�|�ֶּ�b��qet��KU�S�� D�A�4���vR�TF�J T�#2���Sm�9��e��s�;�C;�l���$�"I���s�j��:�C�9�u��5�xr�`��N-)Z'R���uvgD��f���Y^�v�Ζ�`�X>f%S���Vڝ=��Rb�XL~�6B0.�j��	=*�����ٰWz������c #��:Um�?��g�����v���I�ZS��Z�$U��*���c�Z3���M�-�,ڬ��x�j�mςC�9\-��V/�j���%�O]tH�T������Y�(}�g/��x��|Y���"p�Ȧ��	� �=�Ͻ��m�J�Y�Nq�h8?2��3R�E�So���jy�8�K�@��bJ�Ð��R<K=�Գ@Q��[����\�6���}�D��(���T�S�Գ`Q��[�޿����tϻ@0�L��RM�h�z@Q���q>)��ӧ��q�6���❙���.���򩷨��K�|S/�� �#��S����]�E����W��|}��^n�U X�d2�zak�r��.������㍴�?�zSvG})#F-vŰu�f��Bp����[Ϟ��bUDP�m�L5~��5����(�g���9}]}td0]��aS�c�ͬ���&��桛J�j��ER�9#�둪���ha�gq�<���a3h<���`�lܜ)l�Tų�(����,Χ��b�޺]o˯@�EjD��©8�f*v|X�E�s�K#9}�Lo۾7�3L��ZX�ħ�`��Z��3��6���ڻ�7X3��2īT�,C�5`�2�~o�Ʌ�ήwGE�Hq��.l�Td�_kk��s_�I{��;�v����D�Z=�
�\�跤�Q�0��S�|�j���ĸ��	��2ī��֠��F����8uV�!�B��u�x�u�Z�9	�HA��i|��qo?\j׻�*b��`�� ܿF�HA��i��j�x��CԠ@0V����ӫ\f�I F
��qv�զ�����g�X4t�]�ԩ��^0R�h<���S�f�O�����)���܄�IUA|�+#���8봷����|jM�B�װE�`����doQF
��qVmh�^�:����q�5N-�ֹ��##���83��+;\�Ԩ@�p�Ip���!�0�L��Jjj��hߵ�i0J'ՙj܋̬#��Ølw��k���N�wM�qCA(���W��[��0c�i$���a���;�t(ht�Z;�d}��`��E�YL֍隕����]�n���J%��H���d�Z�E-ɭ+�޷�������[P�檵>�#���֬2<�q��M��F�8���u�����,Z�b�ړ��x�_T�w��q�F�sS�y�R�=F�E-ϭ�W:[<��n�f�P�����W��X������ͼ��O��pw47nv�����^�2��q��V�֫�r�N��T��=�fwB(<����:X���d񳡴tޛ��0,q�ᷘ������,ڜ�ɮ�Zm��ָH�����9/*�]9P�9��[�v���v��ݴ	�&B�Q<��N�?f�(�ȁ��QlLv����..�@��8G�.X�
��4P�nܑ�ب�ڝ�c��~� �DT ���#S�+���r�g�m�����n^�[�h𔛫*k��<�.�ޮ�K�ƿ���E�q�;����-�j�ݔ�kr�k=]_^>�R�n�d܀�/-��25���`�k�*g����1�]�q5�z���IUe25X��|�Y�^���s�b�`Yw��a�������݊��z��O�i�(�ۓD���:S�?!i�(ܤ޳8=O�O��;j�
�F)"�<"��ΐрQ�m��8=�;��|���6�#.epX�E��YEՀQ�_��Y�{������=7��T���Wy^A4`��zߚˍ:����5����Ĥ��#`��q�<?���%��v7dC��bR[}F��0��K:���jV��a�:����a0ᒚ��d�g � ���Xo�3��z���@"pN!�Ϲ�j�j�( c�y�kiq��G�Vef�YKeA�*�s�ԀQ ���Ai��̪O���-Ф
�Roxp��U�{TX�_�t�O���'����J�$�߂�+ф�g�(��K:��TU���{�
l1�F�oA�T���/�\��O���Bk��w�J�~��p�ej>�4@Q"{� ���$�<|.ϧ��K"����O2�?F5�E����j��O�.��*�`�u��z���Y���E"���v�<�N��iԭ<�9���	L�[K!���w<��h�9�u끜���qc�O��0o�ה	�S��_�%�8}f�ɢ4Ix���u�a<��"�������O�xk��Hj���Z�j���햁`��	��d���Z��vW���5�=M>k�:��z�@�����D�O��b	*�B���ָum��W����g�L�U(��<���R��<X�5~���1����/��K7�&���&�?4
�/�ӄ�16������������v�`~lȐz
�����9�K��W��4�&��yY�T4�����1�W�MΔ�����Zf��|�j���������� ��M�0�8]��r XD�[��N�'w'*X�׸qy4�]��:��E�Uv]R`�T���[������e��[�X�O��������
$H��Xk�Mnl����aP�W��Ђ������:�ZD�_��\Ɲ�]>�������ˉ��)8��/`��2�e�������v���]w��6.U�>V�%�_�T1�����������"    ��         �  x����r�6�����]/:��@�;�v�Ti�ز�v2�APaL,IY���R�Iz���.�����$MP]���w�s��p����z���IS�����U��(�H2-򜛄�D:.�a��4C1b��1Qc&0�WB^��2B&�Q��Ѻ)���N��~�f���!�O/�ǻ�9Ǒ�A4*҂�D������,"��#F��[��u��-\�ӹ-/�M����\�s߸�k���u�ք��;���$�b�}80���ַ��߈�HK�9G\G�r�G��F�Q��e�}`�q�p��Z�
=� ��y�u� 27��I��qa�� ��).�"�q�9\��p9��s�nĒH*��Dc�DŊ�%N�3!H�C�i�֔;�K� ���3�s���Ic^��ǜ��X���!�O�}�L���ZG
.D(�H�$���{�G�����Z`��n�W���a�'��� �!�s��L��"})��n��ԛ�F���V}�.�������4K�ޮR�Ь�Eg���.MY�q״?��e�|[e����7#�J �DɅ ?�=@�_�J�_�P�B{
``2�<�	�C��K	��� ]���
�t��uP3-|6m�m�y��l���@:u�HU$�}*S�"c�9.����$z*o��Sē��`����q��~�*A<��֥[�na��8o���8�:S�m�Y��q�Yb��!�p
�4q��y�� _�o�������A�TU��GL���n��ʆ^3�Q���~78(�F1����XBi�L�1��\f�|���d|}������P�@�Äyre��fd�n�0�n����sm�pY��TK�c*��x����%��+����� �J�+
�Ih2Dw���|v�n����ͬ���A���h4��U�      3      x������ � �      I      x������ � �      K   �   x���K
�0е|��K����,��9j�PHW��fx��c>0�y1�z'��D��<��
�Y$�48���t�)�L�,�'�3�d�f �	hs�,Z�xV�c��(P�Z@�:#���N�t�bWJ_���)R<u	Q������E�k%Z&=�l�_�J�ĺ���g�-I.G�J����zk��U�'      M     x���[S7 ���W��c��e�pM HJ���d�q�5�_b�c�__�{��U��\�E300`�s��<d��<[�O��x��Z���e:̳Qk�Xe�|�Z�[y>Z�y�j�e�,��&����}BO(��/H�����	~轠�]w3���/�����t�����X������'����|�R��P�6@-HR R�~(��#	��r�� �"Z��I�/����z3˧���j��;k��6���~u��ߋ�`���s�I�ޢHҔ���&"��B�.����q��۹rsQ���zZ�k�oJK͜�BK&���__j�O���X~�jka��L!6���p=�7>l�=	AJ�f#���%�����ݛP�IT���"�`C@4ۻϛ�����t�MSl�6��Q�QT}���ɠ��dCl�ņ�>56)�G�&����Hp6\�a6�b�)4�0��ԁTo6v*6����l�d#��bcz����j���r�҄R�T��`Z��|6 ��P�0�b�<EB���TF��{�U38{;.6�"c�Ifm =|���b��ܣ�&���6�j��\����g6�+�*6
6�l6�E5�قR�Mx�57)M8�$�� tF⛶U3ĆE�IQRqU$0�`���Z�-C���W�M��`J�fu�&�lXG��&'b��l�y�)65ec�
tԮ=bHA�6��?E
6���l6t��f�W�����̳M Z�����i[5��!�fHPlX�i6.+yŦ�6��4$������H�������L@2
>�_s��y�g��\mFZ�աTz�� 1و[�$ݳa`�6+o33�"��@(5��
5U$H�T[^��ն�����^�j�_Ԍ��sR���Xl�Tl̃�Y�*�ݭ��FG�C��%e5��l�`�og������d��?�w�+�f&���rf��24�f���łwE%�f{�2Ǖl�8���ܚ��q��u���4�$�`s�s�5s\��S=�UbF(-�`�`#���>��Zk5��|�c~gk9�v����E��%���4�M��<ܜq�����S�lFH�U6U>Kh�m���ӷ��K����fԓ�C5
�aS<��d�v�Զ��	Ԉ��3~���T;^ �f�� ��ô`�	Í�-��G�3�6ɺ�]ml6g�.s4�" @����^5��17g�!C�yo[]��m�nA��L��b3Ql&��D��(6�f��L��b3�![l&j��f��Lt��L��b3Ql&��D��(6�f��L��b3Ql&��D��(6�f��L��b3Ql&��Da���u�����=��     