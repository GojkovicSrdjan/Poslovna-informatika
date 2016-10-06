/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 12                       */
/* Created on:     7/9/2016 2:38:32 PM                          */
/*==============================================================*/

/*
if exists(select 1 from sys.sysforeignkey where role='FK_ANALITIK_RELATIONS_MAGACINS') then
    alter table "Analitika magacinske kartice"
       delete foreign key FK_ANALITIK_RELATIONS_MAGACINS
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_ANALITIK_RELATIONS_STAVKA P') then
    alter table "Analitika magacinske kartice"
       delete foreign key "FK_ANALITIK_RELATIONS_STAVKA P"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_ARTIKAL_RELATIONS_STAVKE P') then
    alter table Artikal
       delete foreign key "FK_ARTIKAL_RELATIONS_STAVKE P"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_ARTIKAL_RELATIONS_GRUPA AR') then
    alter table Artikal
       delete foreign key "FK_ARTIKAL_RELATIONS_GRUPA AR"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_CLAN KOM_REFERENCE_RADNIK') then
    alter table "Clan komisije"
       delete foreign key "FK_CLAN KOM_REFERENCE_RADNIK"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_CLAN KOM_REFERENCE_POPISNA') then
    alter table "Clan komisije"
       delete foreign key "FK_CLAN KOM_REFERENCE_POPISNA"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_MAGACIN_REFERENCE_PROMETNI') then
    alter table Magacin
       delete foreign key FK_MAGACIN_REFERENCE_PROMETNI
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_MAGACIN_RELATIONS_SEKTOR') then
    alter table Magacin
       delete foreign key FK_MAGACIN_RELATIONS_SEKTOR
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_MAGACINS_RELATIONS_MAGACIN') then
    alter table "Magacinska kartica"
       delete foreign key FK_MAGACINS_RELATIONS_MAGACIN
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_MAGACINS_RELATIONS_POSLOVNA') then
    alter table "Magacinska kartica"
       delete foreign key FK_MAGACINS_RELATIONS_POSLOVNA
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_MAGACINS_RELATIONS_ARTIKAL') then
    alter table "Magacinska kartica"
       delete foreign key FK_MAGACINS_RELATIONS_ARTIKAL
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_POPISNA _RELATIONS_POPISNI') then
    alter table "Popisna komisija"
       delete foreign key "FK_POPISNA _RELATIONS_POPISNI"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_POPISNI _RELATIONS_MAGACIN') then
    alter table "Popisni dokument"
       delete foreign key "FK_POPISNI _RELATIONS_MAGACIN"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_POPISNI _RELATIONS_POSLOVNA') then
    alter table "Popisni dokument"
       delete foreign key "FK_POPISNI _RELATIONS_POSLOVNA"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_POSLOVNA_RELATIONS_PREDUZEC') then
    alter table "Poslovna godina"
       delete foreign key FK_POSLOVNA_RELATIONS_PREDUZEC
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_POSLOVNI_REFERENCE_PREDUZEC') then
    alter table "Poslovni partner"
       delete foreign key FK_POSLOVNI_REFERENCE_PREDUZEC
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_PROMETNI_REFERENCE_VRSTA DO') then
    alter table "Prometni dokument"
       delete foreign key "FK_PROMETNI_REFERENCE_VRSTA DO"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_PROMETNI_REFERENCE_STATUS D') then
    alter table "Prometni dokument"
       delete foreign key "FK_PROMETNI_REFERENCE_STATUS D"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_PROMETNI_RELATIONS_MAGACIN') then
    alter table "Prometni dokument"
       delete foreign key FK_PROMETNI_RELATIONS_MAGACIN
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_PROMETNI_RELATIONS_POSLOVNA') then
    alter table "Prometni dokument"
       delete foreign key FK_PROMETNI_RELATIONS_POSLOVNA
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_PROMETNI_RELATIONS_POSLOVNI') then
    alter table "Prometni dokument"
       delete foreign key FK_PROMETNI_RELATIONS_POSLOVNI
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_RADNIK_RELATIONS_MAGACIN') then
    alter table Radnik
       delete foreign key FK_RADNIK_RELATIONS_MAGACIN
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SEKTOR_RELATIONS_PREDUZEC') then
    alter table Sektor
       delete foreign key FK_SEKTOR_RELATIONS_PREDUZEC
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_STAVKA P_RELATIONS_ARTIKAL') then
    alter table "Stavka prometnog dokumenta"
       delete foreign key "FK_STAVKA P_RELATIONS_ARTIKAL"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_STAVKA P_RELATIONS_PROMETNI') then
    alter table "Stavka prometnog dokumenta"
       delete foreign key "FK_STAVKA P_RELATIONS_PROMETNI"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_STAVKE P_RELATIONS_POPISNI') then
    alter table "Stavke popisa"
       delete foreign key "FK_STAVKE P_RELATIONS_POPISNI"
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_STAVKE P_RELATIONS_ARTIKAL') then
    alter table "Stavke popisa"
       delete foreign key "FK_STAVKE P_RELATIONS_ARTIKAL"
end if;

drop index if exists "Analitika magacinske kartice".RELATIONSHIP_28_FK;

drop index if exists "Analitika magacinske kartice".ANALITIKA_MAGACINSKE_KARTICE_PK;

drop table if exists "Analitika magacinske kartice";

drop index if exists Artikal.RELATIONSHIP_32_FK;

drop index if exists Artikal.RELATIONSHIP_30_FK;

drop table if exists Artikal;

drop index if exists "Clan komisije".POPISNA_KOMISIJA_PK;

drop table if exists "Clan komisije";

drop table if exists "Grupa artikala";

drop index if exists Magacin.MAGACIN_PK;

drop table if exists Magacin;

drop index if exists "Magacinska kartica".RELATIONSHIP_26_FK;

drop index if exists "Magacinska kartica".RELATIONSHIP_24_FK;

drop index if exists "Magacinska kartica".RELATIONSHIP_2_FK;

drop index if exists "Magacinska kartica".MAGACINSKA_KARTICA_PK;

drop table if exists "Magacinska kartica";

drop index if exists "Popisna komisija".POPISNA_KOMISIJA_PK;

drop table if exists "Popisna komisija";

drop index if exists "Popisni dokument".RELATIONSHIP_21_FK;

drop index if exists "Popisni dokument".RELATIONSHIP_20_FK;

drop index if exists "Popisni dokument".POPISNI_DOKUMENT_PK;

drop table if exists "Popisni dokument";

drop index if exists "Poslovna godina".POSLOVNA_GODINA_PK;

drop table if exists "Poslovna godina";

drop table if exists "Poslovni partner";

drop index if exists Preduzece.PREDUZECE_PK;

drop table if exists Preduzece;

drop index if exists "Prometni dokument".RELATIONSHIP_18_FK;

drop index if exists "Prometni dokument".RELATIONSHIP_22_FK;

drop index if exists "Prometni dokument".RELATIONSHIP_9_FK;

drop index if exists "Prometni dokument".PROMETNI_DOKUMENT_PK;

drop table if exists "Prometni dokument";

drop index if exists Radnik.RELATIONSHIP_31_FK;

drop index if exists Radnik.RADNIK_PK;

drop table if exists Radnik;

drop index if exists Sektor.SEKTOR_PK;

drop table if exists Sektor;

drop index if exists "Status dokumenta".MAGACIN_PK;

drop table if exists "Status dokumenta";

drop index if exists "Stavka prometnog dokumenta".RELATIONSHIP_27_FK;

drop index if exists "Stavka prometnog dokumenta".STAVKA_PROMETNOG_DOKUMENTA_PK;

drop table if exists "Stavka prometnog dokumenta";

drop index if exists "Stavke popisa".RELATIONSHIP_29_FK;

drop index if exists "Stavke popisa".STAVKE_POPISA_PK;

drop table if exists "Stavke popisa";

drop index if exists "Vrsta dokumenta".MAGACIN_PK;

drop table if exists "Vrsta dokumenta";*/

/*==============================================================*/
/* Table: "Analitika magacinske kartice"                        */
/*==============================================================*/
create table "Analitika magacinske kartice" 
(
   "stavka prometnog dok" numeric                      null,
   rbr                  numeric                        null,
   datumPromene         date                           null,
   vrDok                date                           null,
   sifraDokumenta       numeric                         null,
   kolicina             numeric                        null,
   cena                 decimal                        null,
   vrednost             decimal                        null,
   amk_id               numeric   identity                     not null,
   "magacinska kartica" numeric                        null,
   "tip promene"        numeric                        not null,
   constraint "PK_ANALITIKA MAGACINSKE KARTIC" primary key clustered (amk_id)
);

/*Tip promene
*/
create table "Tip promene" 
(
   tip_id numeric    identity                    not null,
   naziv                text                   null,
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
   pakovanje            text                   null,
   jedMere              text                   null,
   naziv                text                   null,
   artikal_id           numeric   identity                     not null,
   opis                 text                        null,
   "grupa artikala"     numeric                        null,
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
   nazivGrupe           text                   null,
   constraint "PK_GRUPA ARTIKALA" primary key clustered (ga_id)
);

/*==============================================================*/
/* Table: Magacin                                               */
/*==============================================================*/
create table Magacin 
(
   sektor               numeric                        null,
   naziv                text                  null,
   magacin_id           numeric   identity                     not null,
   "prometni dokument"  numeric                        null,
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
   magacin              numeric                        null,
   "prosecna cena"      money                          null,
   "kolicina ulaza"     numeric                        null,
   "vrednost ulaza"     money                          null,
   "kolicina izlaza"    numeric                        null,
   "vrednost izlaza"    money                          null,
   mk_id                numeric   identity                     not null,
   "poslovna godina"    numeric                        null,
   artikal              numeric                        null,
   "pocetno stanje"     money                          null,
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
   godina               numeric                        null,
   PIB                  numeric                        null,
   constraint "PK_POSLOVNA GODINA" primary key clustered (pg_id)
);

/*==============================================================*/
/* Index: POSLOVNA_GODINA_PK                                    */
/*==============================================================*
create unique clustered index POSLOVNA_GODINA_PK on "Poslovna godina" (
pg_id ASC
);

*==============================================================*/
/* Table: "Poslovni partner"                                    */
/*==============================================================*/
create table "Poslovni partner" 
(
   pp_id                numeric identity                       not null,
   PIB                  numeric                        null,
   naziv                text                  null,
   adresa               text                        null,
   delatnost            text                        null,
   constraint "PK_POSLOVNI PARTNER" primary key clustered (pp_id)
);

/*==============================================================*/
/* Table: Preduzece                                             */
/*==============================================================*/
create table Preduzece 
(
   naziv                text                   null,
   PIB                  numeric  identity                      not null,
   brTelefona           numeric                        null,
   adresa               text                   null,
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
   "broj magacina"      numeric                        null,
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
   naziv                text                   null,
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
   naziv                text                   null,
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
   rbr                  numeric                        null,
   kolicina             numeric                        null,
   cena                 decimal                        null,
   vrednost             decimal                        null,
   sp_id                numeric  identity                      not null,
   artikal              numeric                        null,
   constraint "PK_STAVKA PROMETNOG DOKUMENTA" primary key clustered (sp_id)
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

alter table "Analitika magacinske kartice"
   add constraint FK_ANALITIK_RELATIONS_MAGACINS foreign key ("magacinska kartica")
      references "Magacinska kartica" (mk_id);
      --on update restrict
      --on delete restrict;

alter table "Analitika magacinske kartice"
   add constraint "FK_ANALITIK_RELATIONS_STAVKA P" foreign key ("stavka prometnog dok")
      references "Stavka prometnog dokumenta" (sp_id);
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
   add constraint FK_MAGACIN_REFERENCE_PROMETNI foreign key ("prometni dokument")
      references "Prometni dokument" (pd_id);
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

alter table "Poslovni partner"
   add constraint FK_POSLOVNI_REFERENCE_PREDUZEC foreign key (PIB)
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
      references "Poslovni partner" (pp_id);
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

insert into [Vrsta dokumenta] values ( 'Prijemnica'),( 'Otpremnica'), ( 'Medjumagacinski dokument')

insert into [Status dokumenta] values( 'U formiranju'), ( 'Proknjizen'), ( 'Storniran')

insert into [Tip promene] values('Proknjizen prometni dok'), ( 'Proknjizen popisni dok'), ( 'Proknjizeno pocetno stanje'), ( 'Uradjena nivelacija')