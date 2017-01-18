/*==============================================================*/
/* Table: "Analitika magacinske kartice"                        */
/*==============================================================*/
create table "Analitika magacinske kartice" 
(
   "stavka prometnog dok" numeric                      null,
   vrDok                numeric                        null,
   kolicina             numeric                        null,
   vrednost             money						   null,
   amk_id               numeric   identity             not null,
   smer					text						   null,
   "magacinska kartica" numeric                        not null,
   "tip promene"        numeric                        not null,
   constraint "PK_ANALITIKA MAGACINSKE KARTIC" primary key clustered (amk_id)
);

/*Tip promene
*/
create table "Tip promene" 
(
   tip_id numeric    identity                    not null,
   naziv                text                     null,
   constraint "PK_TIP PROMENE" primary key clustered (tip_id)
);

/*==============================================================*/
/* Index: ANALITIKA_MAGACINSKE_KARTICE_PK                       */
/*==============================================================
create unique clustered index ANALITIKA_MAGACINSKE_KARTICE_PK on "Analitika magacinske kartice" (
amk_id ASC
);

==============================================================*/
/* Index: RELATIONSHIP_28_FK                                    */
/*==============================================================
create index RELATIONSHIP_28_FK on "Analitika magacinske kartice" (
"stavka prometnog dok" ASC
);

==============================================================*/
/* Table: Artikal                                               */
/*==============================================================*/
create table Artikal 
(
   pakovanje            decimal(18,4)                   null,
   jedMere              text                   null,
   naziv                text                  not null,
   artikal_id           numeric   identity                     not null,
   "grupa artikala"     numeric                       not null,
   "stavke popisa"      numeric                        null,
   constraint PK_ARTIKAL primary key clustered (artikal_id)
);

/*==============================================================*/
/* Index: RELATIONSHIP_30_FK                                    */
/*==============================================================
create index RELATIONSHIP_30_FK on Artikal (
"stavke popisa" ASC
);

==============================================================*/
/* Index: RELATIONSHIP_32_FK                                    */
/*==============================================================
create index RELATIONSHIP_32_FK on Artikal (
"grupa artikala" ASC
);

*==============================================================*/
/* Table: "Clan komisije"                                       */
/*==============================================================*/
create table "Clan komisije" 
(
   uloga                text                        null,
   radnik               numeric                        not null,
   "popisna komisija"   numeric                        not null,
   constraint "PK_CLAN KOMISIJE" primary key clustered (radnik, "popisna komisija")
);

/*==============================================================*/
/* Index: POPISNA_KOMISIJA_PK                                   */
/*==============================================================*
create unique clustered index POPISNA_KOMISIJA_PK on "Clan komisije" (
radnik ASC,
"popisna komisija" ASC
);

*==============================================================*/
/* Table: "Grupa artikala"                                      */
/*==============================================================*/
create table "Grupa artikala" 
(
   ga_id                numeric    identity                    not null,
   nazivGrupe           text                   not null,
   constraint "PK_GRUPA ARTIKALA" primary key clustered (ga_id)
);

/*==============================================================*/
/* Table: Magacin                                               */
/*==============================================================*/
create table Magacin 
(
   sektor               numeric                       not null,
   naziv                text                 not null,
   magacin_id           numeric   identity                     not null,
  constraint PK_MAGACIN primary key clustered (magacin_id)
);

/*==============================================================*/
/* Index: MAGACIN_PK                                            */
/*==============================================================*
create unique clustered index MAGACIN_PK on Magacin (
magacin_id ASC
);

*==============================================================*/
/* Table: "Magacinska kartica"                                  */
/*==============================================================*/
create table "Magacinska kartica" 
(
   magacin              numeric                       not null,
   "prosecna cena"      money                          null,
   "zadnja nabavna cena" money							null,
   "maloprodajna cena"	money							null,
   "zadnja prodajna cena" money							null,
   "kolicina ulaza"     numeric                        null,
   "vrednost ulaza"     money                          null,
   "kolicina izlaza"    numeric                        null,
   "vrednost izlaza"    money                          null,
   mk_id                numeric   identity                     not null,
   "poslovna godina"    numeric                        not null,
   artikal              numeric                        not null,
   "pocetna vrednost"   money                          null,
   "pocetna kolicina"	numeric							null,
   "ukupno stanje"      money                          null,
   constraint "PK_MAGACINSKA KARTICA" primary key clustered (mk_id)
);

/*==============================================================*/
/* Index: MAGACINSKA_KARTICA_PK                                 */
/*==============================================================*
create unique clustered index MAGACINSKA_KARTICA_PK on "Magacinska kartica" (
mk_id ASC
);

*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*
create index RELATIONSHIP_2_FK on "Magacinska kartica" (
magacin ASC
);

*==============================================================*/
/* Index: RELATIONSHIP_24_FK                                    */
/*==============================================================*
create index RELATIONSHIP_24_FK on "Magacinska kartica" (
"poslovna godina" ASC
);

*==============================================================*/
/* Index: RELATIONSHIP_26_FK                                    */
/*==============================================================*
create index RELATIONSHIP_26_FK on "Magacinska kartica" (
artikal ASC
);

*==============================================================*/
/* Table: "Popisna komisija"                                    */
/*==============================================================*/
create table "Popisna komisija" 
(
   naziv                text                   null,
   pk_id                numeric identity                       not null,
   "popisni dokument"   numeric                        null,
   opis                 text                        null,
   constraint "PK_POPISNA KOMISIJA" primary key clustered (pk_id)
);

/*==============================================================*/
/* Index: POPISNA_KOMISIJA_PK                                   */
/*==============================================================*
create unique clustered index POPISNA_KOMISIJA_PK on "Popisna komisija" (
pk_id ASC
);

*==============================================================*/
/* Table: "Popisni dokument"                                    */
/*==============================================================*/
create table "Popisni dokument" 
(
   magacin              numeric                        null,
   pd_id                numeric  identity                      not null,
   "poslovna godina"    numeric                        null,
   naziv                text                   null,
   datum                date                           null,
   constraint "PK_POPISNI DOKUMENT" primary key clustered (pd_id)
);

/*==============================================================*/
/* Index: POPISNI_DOKUMENT_PK                                   */
/*==============================================================*
create unique clustered index POPISNI_DOKUMENT_PK on "Popisni dokument" (
pd_id ASC
);

/*==============================================================*/
/* Index: RELATIONSHIP_20_FK                                    */
/*==============================================================*/
create index RELATIONSHIP_20_FK on "Popisni dokument" (
magacin ASC
);

/*==============================================================*/
/* Index: RELATIONSHIP_21_FK                                    */
/*==============================================================*/
create index RELATIONSHIP_21_FK on "Popisni dokument" (
"poslovna godina" ASC
);

*==============================================================*/
/* Table: "Poslovna godina"                                     */
/*==============================================================*/
create table "Poslovna godina" 
(
   pg_id                numeric identity                       not null,
   godina               numeric                       not null,
   PIB                  numeric                       not null,
   zakljucena			tinyint							null,
   constraint "PK_POSLOVNA GODINA" primary key clustered (pg_id)
);

/*==============================================================*/
/* Index: POSLOVNA_GODINA_PK                                    */
/*==============================================================*
create unique clustered index POSLOVNA_GODINA_PK on "Poslovna godina" (
pg_id ASC
);
*/
/*==============================================================*/
/* Table: Preduzece                                             */
/*==============================================================*/
create table Preduzece 
(
   naziv                text                   null,
   PIB                  numeric                      not null,
   brTelefona           numeric                        null,
   adresa               text                   null,
   mesto				text					null,
   constraint PK_PREDUZECE primary key clustered (PIB)
);

/*==============================================================*/
/* Index: PREDUZECE_PK                                          */
/*==============================================================*
create unique clustered index PREDUZECE_PK on Preduzece (
PIB ASC
);

*==============================================================*/
/* Table: "Prometni dokument"                                   */
/*==============================================================*/
create table "Prometni dokument" 
(
   magacin_id           numeric                        null,
   "datum nastanka"     date                           null,
   "datum knjizenja"    date                           null,
   "status dokumenta"   numeric                        null,
   pd_id                numeric identity                       not null,
   "poslovna godina"    numeric                        null,
   "poslovni partner"   numeric                        null,
   "vrsta dokumenta id" numeric                        null,
   constraint "PK_PROMETNI DOKUMENT" primary key clustered (pd_id)
);

/*==============================================================*/
/* Index: PROMETNI_DOKUMENT_PK                                  */
/*==============================================================*
create unique clustered index PROMETNI_DOKUMENT_PK on "Prometni dokument" (
pd_id ASC
);

/*==============================================================*/
/* Index: RELATIONSHIP_9_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_9_FK on "Prometni dokument" (
"poslovni partner" ASC
);

/*==============================================================*/
/* Index: RELATIONSHIP_22_FK                                    */
/*==============================================================*/
create index RELATIONSHIP_22_FK on "Prometni dokument" (
"poslovna godina" ASC
);

/*==============================================================*/
/* Index: RELATIONSHIP_18_FK                                    */
/*==============================================================*/
create index RELATIONSHIP_18_FK on "Prometni dokument" (
magacin_id ASC
);

*==============================================================*/
/* Table: Radnik                                                */
/*==============================================================*/
create table Radnik 
(
   magacin              numeric                        null,
   ime                  text                   null,
   prezime              text                   null,
   pozicija             text                   null,
   radnik_id            numeric  identity                      not null,
   constraint PK_RADNIK primary key clustered (radnik_id)
);

/*==============================================================*/
/* Index: RADNIK_PK                                             */
/*==============================================================*
create unique clustered index RADNIK_PK on Radnik (
radnik_id ASC
);

/*==============================================================*/
/* Index: RELATIONSHIP_31_FK                                    */
/*==============================================================*/
create unique index RELATIONSHIP_31_FK on Radnik (
radnik_id ASC
);

*==============================================================*/
/* Table: Sektor                                                */
/*==============================================================*/
create table Sektor 
(
   PIB                  numeric                        not null,
   adresa               text                   null,
   sektor_id            numeric    identity                    not null,
   naziv                text                  not null,
   constraint PK_SEKTOR primary key clustered (sektor_id)
);

/*==============================================================*/
/* Index: SEKTOR_PK                                             */
/*==============================================================*
create unique clustered index SEKTOR_PK on Sektor (
sektor_id ASC
);

*==============================================================*/
/* Table: "Status dokumenta"                                    */
/*==============================================================*/
create table "Status dokumenta" 
(
   "status dokumenta id" numeric identity                       not null,
   naziv                text                  not null,
   constraint "PK_STATUS DOKUMENTA" primary key clustered ("status dokumenta id")
);

/*==============================================================*/
/* Index: MAGACIN_PK                                            */
/*==============================================================*
create unique clustered index MAGACIN_PK on "Status dokumenta" (
"status dokumenta id" ASC
);

*==============================================================*/
/* Table: "Stavka prometnog dokumenta"                          */
/*==============================================================*/
create table "Stavka prometnog dokumenta" 
(
   "prometni dokument"  numeric                        null,
   rbr                  numeric identity			   not null,
   kolicina             numeric                        null,
   cena                 decimal                        null,
   vrednost             decimal                        null,
   artikal              numeric                        null,
   constraint "PK_STAVKA PROMETNOG DOKUMENTA" primary key clustered (rbr)
);

/*==============================================================*/
/* Index: STAVKA_PROMETNOG_DOKUMENTA_PK                         */
/*==============================================================*
create unique clustered index STAVKA_PROMETNOG_DOKUMENTA_PK on "Stavka prometnog dokumenta" (
sp_id ASC
);

/*==============================================================*/
/* Index: RELATIONSHIP_27_FK                                    */
/*==============================================================*/
create index RELATIONSHIP_27_FK on "Stavka prometnog dokumenta" (
artikal ASC
);

*==============================================================*/
/* Table: "Stavke popisa"                                       */
/*==============================================================*/
create table "Stavke popisa" 
(
   "prometni dokument"  numeric                        null,
   artikal              numeric                        null,
   stanjePoPopisu       text                  null,
   kolicinaUKartici     numeric                        null,
   porNaManjak          numeric                        null,
   sp_id                numeric  identity                      not null,
   constraint "PK_STAVKE POPISA" primary key clustered (sp_id)
);

/*==============================================================*/
/* Index: STAVKE_POPISA_PK                                      */
/*==============================================================*
create unique clustered index STAVKE_POPISA_PK on "Stavke popisa" (
sp_id ASC
);

/*==============================================================*/
/* Index: RELATIONSHIP_29_FK                                    */
/*==============================================================*/
create index RELATIONSHIP_29_FK on "Stavke popisa" (
artikal ASC
);

*==============================================================*/
/* Table: "Vrsta dokumenta"                                     */
/*==============================================================*/
create table "Vrsta dokumenta" 
(
   "vrsta dokumenta id" numeric  identity                      not null,
   naziv                text                   null,
   constraint "PK_VRSTA DOKUMENTA" primary key clustered ("vrsta dokumenta id")
);

/*==============================================================*/
/* Index: MAGACIN_PK                                            */
/*==============================================================*
create unique clustered index MAGACIN_PK on "Vrsta dokumenta" (
"vrsta dokumenta id" ASC
);*/

/*==============================================================*/
/* Table: "Vrsta dokumenta"                                     */
/*==============================================================*/
create table Kupac
(
	magacin			numeric not null,
	dokument			numeric not null,
    primary key (magacin, dokument)
);

alter table Kupac
	add constraint FK_KUPAC_MAGACIN foreign key (magacin)
		references Magacin (magacin_id)
		
alter table Kupac
	add constraint FK_KUPAC_DOKUMENT foreign key (dokument)
		references "Prometni dokument" (pd_id)

alter table "Analitika magacinske kartice"
   add constraint FK_ANALITIK_RELATIONS_VrstaDok foreign key ("vrDok")
      references "Vrsta dokumenta" ("vrsta dokumenta id");

alter table "Analitika magacinske kartice"
   add constraint FK_ANALITIK_RELATIONS_MAGACINS foreign key ("magacinska kartica")
      references "Magacinska kartica" (mk_id);
      --on update restrict
      --on delete restrict;

alter table "Analitika magacinske kartice"
   add constraint "FK_ANALITIK_RELATIONS_STAVKA P" foreign key ("stavka prometnog dok")
      references "Stavka prometnog dokumenta" (rbr);
      --on update restrict
      --on delete restrict;

alter table Artikal
   add constraint "FK_ARTIKAL_RELATIONS_STAVKE P" foreign key ("stavke popisa")
      references "Stavke popisa" (sp_id);
      --on update restrict
      --on delete restrict;
      
ALTER Table "Analitika magacinske kartice"
	add constraint FK_ANALITIK_RELATIONS_TIP foreign key ("tip promene")
		references "Tip promene" (tip_id);

alter table Artikal
   add constraint "FK_ARTIKAL_RELATIONS_GRUPA AR" foreign key ("grupa artikala")
      references "Grupa artikala" (ga_id);
      --on update restrict
      --on delete restrict;

alter table "Clan komisije"
   add constraint "FK_CLAN KOM_REFERENCE_RADNIK" foreign key (radnik)
      references Radnik (radnik_id);
      --on update restrict
      --on delete restrict;

alter table "Clan komisije"
   add constraint "FK_CLAN KOM_REFERENCE_POPISNA" foreign key ("popisna komisija")
      references "Popisna komisija" (pk_id);
      --on update restrict
      --on delete restrict;

alter table Magacin
   add constraint FK_MAGACIN_RELATIONS_SEKTOR foreign key (sektor)
      references Sektor (sektor_id);
      --on update restrict
      --on delete restrict;

alter table "Magacinska kartica"
   add constraint FK_MAGACINS_RELATIONS_MAGACIN foreign key (magacin)
      references Magacin (magacin_id);
      --on update restrict
      --on delete restrict;

alter table "Magacinska kartica"
   add constraint FK_MAGACINS_RELATIONS_POSLOVNA foreign key ("poslovna godina")
      references "Poslovna godina" (pg_id);
      --on update restrict
      --on delete restrict;

alter table "Magacinska kartica"
   add constraint FK_MAGACINS_RELATIONS_ARTIKAL foreign key (artikal)
      references Artikal (artikal_id);
      --on update restrict
      --on delete restrict;

alter table "Popisna komisija"
   add constraint "FK_POPISNA _RELATIONS_POPISNI" foreign key ("popisni dokument")
      references "Popisni dokument" (pd_id);
      --on update restrict
      --on delete restrict;

alter table "Popisni dokument"
   add constraint "FK_POPISNI _RELATIONS_MAGACIN" foreign key (magacin)
      references Magacin (magacin_id);
      --on update restrict
      --on delete restrict;

alter table "Popisni dokument"
   add constraint "FK_POPISNI _RELATIONS_POSLOVNA" foreign key ("poslovna godina")
      references "Poslovna godina" (pg_id);
      --on update restrict
      --on delete restrict;

alter table "Poslovna godina"
   add constraint FK_POSLOVNA_RELATIONS_PREDUZEC foreign key (PIB)
      references Preduzece (PIB);
      --on update restrict
      --on delete restrict;

alter table "Prometni dokument"
   add constraint "FK_PROMETNI_REFERENCE_VRSTA DO" foreign key ("vrsta dokumenta id")
      references "Vrsta dokumenta" ("vrsta dokumenta id");
      --on update restrict
      --on delete restrict;

alter table "Prometni dokument"
   add constraint "FK_PROMETNI_REFERENCE_STATUS D" foreign key ("status dokumenta")
      references "Status dokumenta" ("status dokumenta id");
      --on update restrict
      --on delete restrict;

alter table "Prometni dokument"
   add constraint FK_PROMETNI_RELATIONS_MAGACIN foreign key (magacin_id)
      references Magacin (magacin_id);
      --on update restrict
      --on delete restrict;

alter table "Prometni dokument"
   add constraint FK_PROMETNI_RELATIONS_POSLOVNA foreign key ("poslovna godina")
      references "Poslovna godina" (pg_id);
      --on update restrict
      --on delete restrict;

alter table "Prometni dokument"
   add constraint FK_PROMETNI_RELATIONS_POSLOVNI foreign key ("poslovni partner")
      references Preduzece (PIB);
      --on update restrict
      --on delete restrict;

alter table Radnik
   add constraint FK_RADNIK_RELATIONS_MAGACIN foreign key (magacin)
      references Magacin (magacin_id);
      --on update restrict
      --on delete restrict;

alter table Sektor
   add constraint FK_SEKTOR_RELATIONS_PREDUZEC foreign key (PIB)
      references Preduzece (PIB);
      --on update restrict
      --on delete restrict;

alter table "Stavka prometnog dokumenta"
   add constraint "FK_STAVKA P_RELATIONS_ARTIKAL" foreign key (artikal)
      references Artikal (artikal_id);
      --on update restrict
      --on delete restrict;

alter table "Stavka prometnog dokumenta"
   add constraint "FK_STAVKA P_RELATIONS_PROMETNI" foreign key ("prometni dokument")
      references "Prometni dokument" (pd_id);
      --on update restrict
      --on delete restrict;

alter table "Stavke popisa"
   add constraint "FK_STAVKE P_RELATIONS_POPISNI" foreign key ("prometni dokument")
      references "Popisni dokument" (pd_id);
      --on update restrict
      --on delete restrict;

alter table "Stavke popisa"
   add constraint "FK_STAVKE P_RELATIONS_ARTIKAL" foreign key (artikal)
      references Artikal (artikal_id);
      --on update restrict
      --on delete restrict;

insert into [Vrsta dokumenta] values ( 'Primka'),( 'Otpremnica'), ( 'Medjumagacinski dokument')

insert into [Status dokumenta] values( 'U formiranju'), ( 'Proknjizen'), ( 'Storniran')

insert into [Tip promene] values('Proknjizen prometni dok'), ( 'Proknjizen popisni dok'), ( 'Proknjizeno pocetno stanje'), ( 'Uradjena nivelacija')