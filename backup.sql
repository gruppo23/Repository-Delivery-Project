PGDMP                         x            oopbd_project    13.1    13.1 8                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            !           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            "           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            #           1262    24678    oopbd_project    DATABASE     i   CREATE DATABASE oopbd_project WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Italian_Italy.1252';
    DROP DATABASE oopbd_project;
                uni_project    false            $           0    0    DATABASE oopbd_project    COMMENT     N   COMMENT ON DATABASE oopbd_project IS 'Database per il progetto di OOP e BD.';
                   uni_project    false    3107                        3079    24687    pgcrypto 	   EXTENSION     <   CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;
    DROP EXTENSION pgcrypto;
                   false            %           0    0    EXTENSION pgcrypto    COMMENT     <   COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';
                        false    2                       1255    32967 $   func_delete_prod_before_restaurant()    FUNCTION     5  CREATE FUNCTION public.func_delete_prod_before_restaurant() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

	declare
		curs cursor for (
			select rp.id_product from restaurant_product as rp 
			inner join restaurant as r on rp.id_restaurant = old.id_restaurant
		);
		rec record;
		internalRecord record;
		
	begin
		
		open curs;
		loop
		
			fetch curs into rec;
				for internalRecord in (select id_product, count(id_product) as numRec from restaurant_product where id_product = rec.id_product group by id_product)
				loop
					if internalRecord.numRec = 1 then
						if internalRecord.id_product = rec.id_product then
							delete from product where id = rec.id_product;
						end if;
					end if;
					exit when not found;
				end loop;
			exit when not found;
		
		end loop;
		close curs;

		return old;
end $$;
 ;   DROP FUNCTION public.func_delete_prod_before_restaurant();
       public          postgres    false                       1255    33004    insertintoallergen() 	   PROCEDURE     �  CREATE PROCEDURE public.insertintoallergen()
    LANGUAGE plpgsql
    AS $$
declare
		curs_select_allergen cursor for (select count(*) as num_allergen from allergen);
		rec_allergen record;
			
	begin
		
		open curs_select_allergen;
		fetch curs_select_allergen into rec_allergen;
		
		if rec_allergen.num_allergen = 0 then
			INSERT INTO allergen(id_allergen,name_allergen)
			VALUES ('01','glutine'),
				   ('02','crostacei e derivati'),
				   ('03','uova e derivati'),
				   ('04','pesce e derivati'),
				   ('05','arachidi e derivati'),
				   ('06','soia e derivati'),
				   ('07','latte e derivati'),
				   ('08','frutta a guscio e derivati'),
				   ('09','sedano e derivati'),
				   ('10','senape e derivati'),
				   ('11','semi di sesamo e derivati'),
				   ('12','anidride solforosa e solfiti'),
				   ('13','lupini e derivati'),
				   ('14','molluschi e derivati'),
				   ('15','fave e/o piselli');
		end if;
		
		close curs_select_allergen;
	
end
$$;
 ,   DROP PROCEDURE public.insertintoallergen();
       public          postgres    false            �            1259    32999    allergen    TABLE     �   CREATE TABLE public.allergen (
    id_allergen character varying(2) NOT NULL,
    name_allergen character varying(28) NOT NULL
);
    DROP TABLE public.allergen;
       public         heap    postgres    false            �            1259    33005    allergyproduct    TABLE     ]   CREATE TABLE public.allergyproduct (
    id_allergen character varying(2),
    id integer
);
 "   DROP TABLE public.allergyproduct;
       public         heap    postgres    false            �            1259    32994    customer    TABLE     W  CREATE TABLE public.customer (
    fiscal_code character(16) NOT NULL,
    name character varying(20) NOT NULL,
    surname character varying(20) NOT NULL,
    date_n character(10) NOT NULL,
    city character varying(30) NOT NULL,
    cap character(5) NOT NULL,
    address character varying(30) NOT NULL,
    phone character(10) NOT NULL
);
    DROP TABLE public.customer;
       public         heap    postgres    false            �            1259    32969    drivers    TABLE     x  CREATE TABLE public.drivers (
    fiscal_code character(16) NOT NULL,
    name character varying(30) NOT NULL,
    surname character varying(30) NOT NULL,
    city character varying(20) NOT NULL,
    cap character(5) NOT NULL,
    address character varying(20) NOT NULL,
    phone character(10) NOT NULL,
    gender character(1) NOT NULL,
    data_n character(10) NOT NULL
);
    DROP TABLE public.drivers;
       public         heap    postgres    false            �            1259    24783    product    TABLE     �   CREATE TABLE public.product (
    id integer NOT NULL,
    name_product text NOT NULL,
    price real NOT NULL,
    vat_number real NOT NULL,
    img_path text NOT NULL,
    manage_quantity boolean DEFAULT false
);
    DROP TABLE public.product;
       public         heap    postgres    false            �            1259    24781    product_id_seq    SEQUENCE     �   CREATE SEQUENCE public.product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.product_id_seq;
       public          postgres    false    206            &           0    0    product_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.product_id_seq OWNED BY public.product.id;
          public          postgres    false    205            �            1259    24743 
   restaurant    TABLE     -  CREATE TABLE public.restaurant (
    id_restaurant character(5) NOT NULL,
    name character varying(30) NOT NULL,
    id_tipology integer NOT NULL,
    city character varying(50) NOT NULL,
    address character varying(50) NOT NULL,
    cap character(5) NOT NULL,
    phone character(10) NOT NULL
);
    DROP TABLE public.restaurant;
       public         heap    postgres    false            �            1259    24839    restaurant_product    TABLE     �   CREATE TABLE public.restaurant_product (
    id_relation integer NOT NULL,
    id_product integer NOT NULL,
    id_restaurant character(5) NOT NULL,
    quantity integer NOT NULL
);
 &   DROP TABLE public.restaurant_product;
       public         heap    postgres    false            �            1259    24837 "   restaurant_product_id_relation_seq    SEQUENCE     �   CREATE SEQUENCE public.restaurant_product_id_relation_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 9   DROP SEQUENCE public.restaurant_product_id_relation_seq;
       public          postgres    false    208            '           0    0 "   restaurant_product_id_relation_seq    SEQUENCE OWNED BY     i   ALTER SEQUENCE public.restaurant_product_id_relation_seq OWNED BY public.restaurant_product.id_relation;
          public          postgres    false    207            �            1259    24732    restaurant_tipology    TABLE     �   CREATE TABLE public.restaurant_tipology (
    id_tipology integer NOT NULL,
    tipology text NOT NULL,
    description character varying(250) NOT NULL
);
 '   DROP TABLE public.restaurant_tipology;
       public         heap    postgres    false            �            1259    24730 #   restaurant_tipology_id_tipology_seq    SEQUENCE     �   CREATE SEQUENCE public.restaurant_tipology_id_tipology_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 :   DROP SEQUENCE public.restaurant_tipology_id_tipology_seq;
       public          postgres    false    203            (           0    0 #   restaurant_tipology_id_tipology_seq    SEQUENCE OWNED BY     k   ALTER SEQUENCE public.restaurant_tipology_id_tipology_seq OWNED BY public.restaurant_tipology.id_tipology;
          public          postgres    false    202            �            1259    24679    users    TABLE     �   CREATE TABLE public.users (
    id_user integer NOT NULL,
    name_user text NOT NULL,
    surname_user text NOT NULL,
    username text NOT NULL,
    password text NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false            q           2604    24786 
   product id    DEFAULT     h   ALTER TABLE ONLY public.product ALTER COLUMN id SET DEFAULT nextval('public.product_id_seq'::regclass);
 9   ALTER TABLE public.product ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    205    206    206            s           2604    24842    restaurant_product id_relation    DEFAULT     �   ALTER TABLE ONLY public.restaurant_product ALTER COLUMN id_relation SET DEFAULT nextval('public.restaurant_product_id_relation_seq'::regclass);
 M   ALTER TABLE public.restaurant_product ALTER COLUMN id_relation DROP DEFAULT;
       public          postgres    false    208    207    208            p           2604    24735    restaurant_tipology id_tipology    DEFAULT     �   ALTER TABLE ONLY public.restaurant_tipology ALTER COLUMN id_tipology SET DEFAULT nextval('public.restaurant_tipology_id_tipology_seq'::regclass);
 N   ALTER TABLE public.restaurant_tipology ALTER COLUMN id_tipology DROP DEFAULT;
       public          postgres    false    203    202    203                      0    32999    allergen 
   TABLE DATA           >   COPY public.allergen (id_allergen, name_allergen) FROM stdin;
    public          postgres    false    211   II                 0    33005    allergyproduct 
   TABLE DATA           9   COPY public.allergyproduct (id_allergen, id) FROM stdin;
    public          postgres    false    212   J                 0    32994    customer 
   TABLE DATA           a   COPY public.customer (fiscal_code, name, surname, date_n, city, cap, address, phone) FROM stdin;
    public          postgres    false    210   'J                 0    32969    drivers 
   TABLE DATA           h   COPY public.drivers (fiscal_code, name, surname, city, cap, address, phone, gender, data_n) FROM stdin;
    public          postgres    false    209   �J                 0    24783    product 
   TABLE DATA           a   COPY public.product (id, name_product, price, vat_number, img_path, manage_quantity) FROM stdin;
    public          postgres    false    206   �J                 0    24743 
   restaurant 
   TABLE DATA           a   COPY public.restaurant (id_restaurant, name, id_tipology, city, address, cap, phone) FROM stdin;
    public          postgres    false    204   pK                 0    24839    restaurant_product 
   TABLE DATA           ^   COPY public.restaurant_product (id_relation, id_product, id_restaurant, quantity) FROM stdin;
    public          postgres    false    208   �K                 0    24732    restaurant_tipology 
   TABLE DATA           Q   COPY public.restaurant_tipology (id_tipology, tipology, description) FROM stdin;
    public          postgres    false    203   �K                 0    24679    users 
   TABLE DATA           U   COPY public.users (id_user, name_user, surname_user, username, password) FROM stdin;
    public          postgres    false    201   IL       )           0    0    product_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.product_id_seq', 59, true);
          public          postgres    false    205            *           0    0 "   restaurant_product_id_relation_seq    SEQUENCE SET     Q   SELECT pg_catalog.setval('public.restaurant_product_id_relation_seq', 44, true);
          public          postgres    false    207            +           0    0 #   restaurant_tipology_id_tipology_seq    SEQUENCE SET     R   SELECT pg_catalog.setval('public.restaurant_tipology_id_tipology_seq', 29, true);
          public          postgres    false    202            �           2606    33003    allergen allergen_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.allergen
    ADD CONSTRAINT allergen_pkey PRIMARY KEY (id_allergen);
 @   ALTER TABLE ONLY public.allergen DROP CONSTRAINT allergen_pkey;
       public            postgres    false    211            �           2606    33019    allergyproduct constraint_uq 
   CONSTRAINT     b   ALTER TABLE ONLY public.allergyproduct
    ADD CONSTRAINT constraint_uq UNIQUE (id_allergen, id);
 F   ALTER TABLE ONLY public.allergyproduct DROP CONSTRAINT constraint_uq;
       public            postgres    false    212    212            �           2606    32998    customer customer_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (fiscal_code);
 @   ALTER TABLE ONLY public.customer DROP CONSTRAINT customer_pkey;
       public            postgres    false    210            �           2606    32973    drivers drivers_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_pkey PRIMARY KEY (fiscal_code);
 >   ALTER TABLE ONLY public.drivers DROP CONSTRAINT drivers_pkey;
       public            postgres    false    209                       2606    24791    product product_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.product DROP CONSTRAINT product_pkey;
       public            postgres    false    206            }           2606    24747    restaurant restaurant_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.restaurant
    ADD CONSTRAINT restaurant_pkey PRIMARY KEY (id_restaurant);
 D   ALTER TABLE ONLY public.restaurant DROP CONSTRAINT restaurant_pkey;
       public            postgres    false    204            �           2606    24844 *   restaurant_product restaurant_product_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY public.restaurant_product
    ADD CONSTRAINT restaurant_product_pkey PRIMARY KEY (id_relation);
 T   ALTER TABLE ONLY public.restaurant_product DROP CONSTRAINT restaurant_product_pkey;
       public            postgres    false    208            y           2606    24740 ,   restaurant_tipology restaurant_tipology_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.restaurant_tipology
    ADD CONSTRAINT restaurant_tipology_pkey PRIMARY KEY (id_tipology);
 V   ALTER TABLE ONLY public.restaurant_tipology DROP CONSTRAINT restaurant_tipology_pkey;
       public            postgres    false    203            u           2606    24686    users uni_users_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uni_users_pkey PRIMARY KEY (id_user);
 >   ALTER TABLE ONLY public.users DROP CONSTRAINT uni_users_pkey;
       public            postgres    false    201            {           2606    24742    restaurant_tipology uq 
   CONSTRAINT     U   ALTER TABLE ONLY public.restaurant_tipology
    ADD CONSTRAINT uq UNIQUE (tipology);
 @   ALTER TABLE ONLY public.restaurant_tipology DROP CONSTRAINT uq;
       public            postgres    false    203            w           2606    24754    users username_unique 
   CONSTRAINT     T   ALTER TABLE ONLY public.users
    ADD CONSTRAINT username_unique UNIQUE (username);
 ?   ALTER TABLE ONLY public.users DROP CONSTRAINT username_unique;
       public            postgres    false    201            �           2620    32968 2   restaurant trig_func_delete_prod_before_restaurant    TRIGGER     �   CREATE TRIGGER trig_func_delete_prod_before_restaurant BEFORE DELETE ON public.restaurant FOR EACH ROW EXECUTE FUNCTION public.func_delete_prod_before_restaurant();
 K   DROP TRIGGER trig_func_delete_prod_before_restaurant ON public.restaurant;
       public          postgres    false    204    260            �           2606    24748    restaurant fk1    FK CONSTRAINT     �   ALTER TABLE ONLY public.restaurant
    ADD CONSTRAINT fk1 FOREIGN KEY (id_tipology) REFERENCES public.restaurant_tipology(id_tipology);
 8   ALTER TABLE ONLY public.restaurant DROP CONSTRAINT fk1;
       public          postgres    false    2937    203    204            �           2606    33008    allergyproduct fk_allergen    FK CONSTRAINT     �   ALTER TABLE ONLY public.allergyproduct
    ADD CONSTRAINT fk_allergen FOREIGN KEY (id_allergen) REFERENCES public.allergen(id_allergen);
 D   ALTER TABLE ONLY public.allergyproduct DROP CONSTRAINT fk_allergen;
       public          postgres    false    212    211    2951            �           2606    24845    restaurant_product fk_prod    FK CONSTRAINT     �   ALTER TABLE ONLY public.restaurant_product
    ADD CONSTRAINT fk_prod FOREIGN KEY (id_product) REFERENCES public.product(id) ON DELETE CASCADE;
 D   ALTER TABLE ONLY public.restaurant_product DROP CONSTRAINT fk_prod;
       public          postgres    false    206    208    2943            �           2606    33013    allergyproduct fk_product    FK CONSTRAINT     u   ALTER TABLE ONLY public.allergyproduct
    ADD CONSTRAINT fk_product FOREIGN KEY (id) REFERENCES public.product(id);
 C   ALTER TABLE ONLY public.allergyproduct DROP CONSTRAINT fk_product;
       public          postgres    false    212    206    2943            �           2606    24850    restaurant_product fk_rest    FK CONSTRAINT     �   ALTER TABLE ONLY public.restaurant_product
    ADD CONSTRAINT fk_rest FOREIGN KEY (id_restaurant) REFERENCES public.restaurant(id_restaurant) ON DELETE CASCADE;
 D   ALTER TABLE ONLY public.restaurant_product DROP CONSTRAINT fk_rest;
       public          postgres    false    208    2941    204               �   x�U�I� E����:ܥ���C�_���zֳ�l�UNd�|�R�[��
_X��|�B3�$�[ ����_ ������j�ki��E�5���B��n�0��8�����<�1&�Y�qͽ����O��Ip39ƞi�|�������B1��=�1zp            x�3��4������ 
��         b   x�5ʱ
� ����@qՠ�Z\4��!B����3�́��6�y'n�cMR��[ɱ��Z�z�CgP���H`Q4�}G��:�Ϩ�WJ��i�         N   x�s�60p10w�0���LLLc�Ԝ�Ģ�D]���D_ 3/�����Є3%55�� 8}9�Jsr��+F��� 
��         i   x��A
�0���.	�L3m��z w���R� x{}�7L��T�D��t�UogS]SkPͨ���������/%x��Hɖ9�v�m^���჈{O�F�1�S�         I   x�2000�tIT(������42��K,�����LT �������T�<=#cN�jcNCCKc33cs�=... �k�            x������ � �         S   x�32����r�t�����w�tTp�T��r�qUP��P�2���uv��s�q��2��t��u�DR����� �	�         `   x�3�t�+�����tN,.N-N�L�+�T1JT10Sq7��u�)�vt1�+M6(�.�L,5�����/(1I*�3	(sM/3u)	�-�N��t6����� İ�     