-- Generated by Oracle SQL Developer Data Modeler 18.3.0.268.1156
--   at:        2019-11-26 12:12:35 CET
--   site:      Oracle Database 11g
--   type:      Oracle Database 11g



CREATE TABLE danie_na_zamowieniu (
    rachunek_id_rachunku   INTEGER NOT NULL,
    ilosc                  SMALLINT NOT NULL,
    menu_nazwa_dania       VARCHAR2(32)
);



CREATE TABLE kelner (
    id_roli        INTEGER NOT NULL,
    srednia_ocen   FLOAT(2) NOT NULL
);

ALTER TABLE kelner ADD CONSTRAINT kelner_pk PRIMARY KEY ( id_roli );

CREATE TABLE kucharz (
    id_roli   INTEGER NOT NULL
);

ALTER TABLE kucharz ADD CONSTRAINT kucharz_pk PRIMARY KEY ( id_roli );

CREATE TABLE magazyn (
    nazwa_towaru   VARCHAR2(32) NOT NULL,
    ilosc          INTEGER NOT NULL
);

ALTER TABLE magazyn ADD CONSTRAINT magazyn_pk PRIMARY KEY ( nazwa_towaru );

CREATE TABLE menedzer (
    id_roli        INTEGER NOT NULL,
    srednia_ocen   FLOAT(2) NOT NULL
);

ALTER TABLE menedzer ADD CONSTRAINT menedzer_pk PRIMARY KEY ( id_roli );

CREATE TABLE menu (
    nazwa_dania   VARCHAR2(32) NOT NULL,
    cena          FLOAT(2) NOT NULL
);

ALTER TABLE menu ADD CONSTRAINT menu_pk PRIMARY KEY ( nazwa_dania );

CREATE TABLE pracownik (
    id_prac             INTEGER NOT NULL,
    imie                VARCHAR2(20) NOT NULL,
    nazwisko            VARCHAR2(30) NOT NULL,
    data_zatrudnienia   DATE NOT NULL,
    czy_kelner          BLOB NOT NULL,
    czy_kucharz         BLOB NOT NULL,
    czy_menedzer        BLOB NOT NULL
);

ALTER TABLE pracownik ADD CONSTRAINT pracownik_pk PRIMARY KEY ( id_prac );

CREATE TABLE pracownik_na_zmianie (
    pracownik_id_prac         INTEGER,
    data                      DATE NOT NULL,
    ilosc_godzin              SMALLINT NOT NULL,
    stawka                    FLOAT(2) NOT NULL,
    pracownik_na_zmianie_id   NUMBER NOT NULL
);

ALTER TABLE pracownik_na_zmianie ADD CONSTRAINT pracownik_na_zmianie_pk PRIMARY KEY ( pracownik_na_zmianie_id );

CREATE TABLE rachunek (
    id_rachunku        INTEGER NOT NULL,
    ocena              INTEGER,
    sumaryczna_cena    FLOAT(2) NOT NULL,
    oplacono           BLOB,
    nr_stolika         SMALLINT NOT NULL,
    menedzer_id_roli   INTEGER NOT NULL,
    kelner_id_roli     INTEGER NOT NULL,
    data               DATE NOT NULL
);

ALTER TABLE rachunek ADD CONSTRAINT rachunek_pk PRIMARY KEY ( id_rachunku );

CREATE TABLE relation_1 (
    pracownik_id_prac   INTEGER NOT NULL,
    rola_id_roli        INTEGER NOT NULL
);

ALTER TABLE relation_1 ADD CONSTRAINT relation_1_pk PRIMARY KEY ( pracownik_id_prac,
                                                                  rola_id_roli );

CREATE TABLE rola (
    id_roli                   INTEGER NOT NULL,
    pracownik_na_zmianie_id   NUMBER NOT NULL,
    typ                       CHAR(2 CHAR)
);

CREATE UNIQUE INDEX rola__idx ON
    rola (
        pracownik_na_zmianie_id
    ASC );

ALTER TABLE rola ADD CONSTRAINT rola_pk PRIMARY KEY ( id_roli );

CREATE TABLE zamowiony_towar (
    menedzer_id_roli       INTEGER NOT NULL,
    ilosc_sztuk            SMALLINT,
    id_zamowienia          INTEGER NOT NULL,
    magazyn_nazwa_towaru   VARCHAR2(32),
    czy_dostarczony        BLOB NOT NULL
);

ALTER TABLE zamowiony_towar ADD CONSTRAINT zamowiony_towar_pk PRIMARY KEY ( id_zamowienia );

ALTER TABLE danie_na_zamowieniu
    ADD CONSTRAINT danie_na_zamowieniu_menu_fk FOREIGN KEY ( menu_nazwa_dania )
        REFERENCES menu ( nazwa_dania );

ALTER TABLE danie_na_zamowieniu
    ADD CONSTRAINT danie_na_zamowieniu_rach_fk FOREIGN KEY ( rachunek_id_rachunku )
        REFERENCES rachunek ( id_rachunku );

ALTER TABLE kelner
    ADD CONSTRAINT kelner_rola_fk FOREIGN KEY ( id_roli )
        REFERENCES rola ( id_roli );

ALTER TABLE kucharz
    ADD CONSTRAINT kucharz_rola_fk FOREIGN KEY ( id_roli )
        REFERENCES rola ( id_roli );

ALTER TABLE menedzer
    ADD CONSTRAINT menedzer_rola_fk FOREIGN KEY ( id_roli )
        REFERENCES rola ( id_roli );

ALTER TABLE pracownik_na_zmianie
    ADD CONSTRAINT pracownik_na_zmianie_prac_fk FOREIGN KEY ( pracownik_id_prac )
        REFERENCES pracownik ( id_prac );

ALTER TABLE rachunek
    ADD CONSTRAINT rachunek_kelner_fk FOREIGN KEY ( kelner_id_roli )
        REFERENCES kelner ( id_roli );

ALTER TABLE rachunek
    ADD CONSTRAINT rachunek_menedzer_fk FOREIGN KEY ( menedzer_id_roli )
        REFERENCES menedzer ( id_roli );

ALTER TABLE relation_1
    ADD CONSTRAINT relation_1_pracownik_fk FOREIGN KEY ( pracownik_id_prac )
        REFERENCES pracownik ( id_prac );

ALTER TABLE relation_1
    ADD CONSTRAINT relation_1_rola_fk FOREIGN KEY ( rola_id_roli )
        REFERENCES rola ( id_roli );

ALTER TABLE rola
    ADD CONSTRAINT rola_pracownik_na_zmianie_fk FOREIGN KEY ( pracownik_na_zmianie_id )
        REFERENCES pracownik_na_zmianie ( pracownik_na_zmianie_id );

ALTER TABLE zamowiony_towar
    ADD CONSTRAINT zamowiony_towar_magazyn_fk FOREIGN KEY ( magazyn_nazwa_towaru )
        REFERENCES magazyn ( nazwa_towaru );

ALTER TABLE zamowiony_towar
    ADD CONSTRAINT zamowiony_towar_menedzer_fk FOREIGN KEY ( menedzer_id_roli )
        REFERENCES menedzer ( id_roli );

ALTER TABLE danie_na_zamowieniu
    ADD CONSTRAINT danie_na_zamowieniu_menu_fk FOREIGN KEY ( menu_nazwa_dania )
        REFERENCES menu ( nazwa_dania );

ALTER TABLE danie_na_zamowieniu
    ADD CONSTRAINT danie_na_zamowieniu_rach_fk FOREIGN KEY ( rachunek_id_rachunku )
        REFERENCES rachunek ( id_rachunku );

ALTER TABLE kelner
    ADD CONSTRAINT kelner_rola_fk FOREIGN KEY ( id_roli )
        REFERENCES rola ( id_roli );

ALTER TABLE kucharz
    ADD CONSTRAINT kucharz_rola_fk FOREIGN KEY ( id_roli )
        REFERENCES rola ( id_roli );

ALTER TABLE menedzer
    ADD CONSTRAINT menedzer_rola_fk FOREIGN KEY ( id_roli )
        REFERENCES rola ( id_roli );

ALTER TABLE pracownik_na_zmianie
    ADD CONSTRAINT pracownik_na_zmianie_prac_fk FOREIGN KEY ( pracownik_id_prac )
        REFERENCES pracownik ( id_prac );

ALTER TABLE rachunek
    ADD CONSTRAINT rachunek_kelner_fk FOREIGN KEY ( kelner_id_roli )
        REFERENCES kelner ( id_roli );

ALTER TABLE rachunek
    ADD CONSTRAINT rachunek_menedzer_fk FOREIGN KEY ( menedzer_id_roli )
        REFERENCES menedzer ( id_roli );

ALTER TABLE relation_1
    ADD CONSTRAINT relation_1_pracownik_fk FOREIGN KEY ( pracownik_id_prac )
        REFERENCES pracownik ( id_prac );

ALTER TABLE relation_1
    ADD CONSTRAINT relation_1_rola_fk FOREIGN KEY ( rola_id_roli )
        REFERENCES rola ( id_roli );

ALTER TABLE rola
    ADD CONSTRAINT rola_pracownik_na_zmianie_fk FOREIGN KEY ( pracownik_na_zmianie_id )
        REFERENCES pracownik_na_zmianie ( pracownik_na_zmianie_id );

ALTER TABLE zamowiony_towar
    ADD CONSTRAINT zamowiony_towar_magazyn_fk FOREIGN KEY ( magazyn_nazwa_towaru )
        REFERENCES magazyn ( nazwa_towaru );

ALTER TABLE zamowiony_towar
    ADD CONSTRAINT zamowiony_towar_menedzer_fk FOREIGN KEY ( menedzer_id_roli )
        REFERENCES menedzer ( id_roli );

CREATE OR REPLACE TRIGGER arc_fkarc_1_menedzer BEFORE
    INSERT OR UPDATE OF id_roli ON menedzer
    FOR EACH ROW
DECLARE
    d   CHAR(2 CHAR);
BEGIN
    SELECT
        a.typ
    INTO d
    FROM
        rola a
    WHERE
        a.id_roli = :new.id_roli;

    IF ( d IS NULL OR d <> 'ME' ) THEN
        raise_application_error(-20223, 'FK MENEDZER_ROLA_FK in Table MENEDZER violates Arc constraint on Table ROLA - discriminator column typ doesn''t have value ''ME'''
        );
    END IF;

EXCEPTION
    WHEN no_data_found THEN
        NULL;
    WHEN OTHERS THEN
        RAISE;
END;
/

CREATE OR REPLACE TRIGGER arc_fkarc_1_kucharz BEFORE
    INSERT OR UPDATE OF id_roli ON kucharz
    FOR EACH ROW
DECLARE
    d   CHAR(2 CHAR);
BEGIN
    SELECT
        a.typ
    INTO d
    FROM
        rola a
    WHERE
        a.id_roli = :new.id_roli;

    IF ( d IS NULL OR d <> 'KU' ) THEN
        raise_application_error(-20223, 'FK KUCHARZ_ROLA_FK in Table KUCHARZ violates Arc constraint on Table ROLA - discriminator column typ doesn''t have value ''KU'''
        );
    END IF;

EXCEPTION
    WHEN no_data_found THEN
        NULL;
    WHEN OTHERS THEN
        RAISE;
END;
/

CREATE OR REPLACE TRIGGER arc_fkarc_1_kelner BEFORE
    INSERT OR UPDATE OF id_roli ON kelner
    FOR EACH ROW
DECLARE
    d   CHAR(2 CHAR);
BEGIN
    SELECT
        a.typ
    INTO d
    FROM
        rola a
    WHERE
        a.id_roli = :new.id_roli;

    IF ( d IS NULL OR d <> 'KE' ) THEN
        raise_application_error(-20223, 'FK KELNER_ROLA_FK in Table KELNER violates Arc constraint on Table ROLA - discriminator column typ doesn''t have value ''KE'''
        );
    END IF;

EXCEPTION
    WHEN no_data_found THEN
        NULL;
    WHEN OTHERS THEN
        RAISE;
END;
/

CREATE SEQUENCE pracownik_na_zmianie_pracownik START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER pracownik_na_zmianie_pracownik BEFORE
    INSERT ON pracownik_na_zmianie
    FOR EACH ROW
    WHEN ( new.pracownik_na_zmianie_id IS NULL )
BEGIN
    :new.pracownik_na_zmianie_id := pracownik_na_zmianie_pracownik.nextval;
END;
/



-- Oracle SQL Developer Data Modeler Summary Report: 
-- 
-- CREATE TABLE                            12
-- CREATE INDEX                             1
-- ALTER TABLE                             37
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           4
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          1
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0



--poprawienie tabeli rachunek
alter table rachunek drop(menedzer_id_roli, kelner_id_roli);
alter table rachunek add(id_pracownika INTEGER NOT NULL);
alter table rachunek add(data_rachunku TIMESTAMP NOT NULL);
alter table rachunek drop(data);
alter table rachunek drop(oplacono);
alter table rachunek add(oplacono varchar(1));

CREATE SEQUENCE ID_PRAC_seq
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE ID_ZAMOWIENIA_seq -- DOTYCZY ZAMOWIONEGO DO MAGAZYNU TOWARU
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE ID_RACHUNKU_seq -- DOTYCZY RACHUNK�W KLIENT�W
START WITH 1 INCREMENT BY 1;
/
CREATE OR REPLACE PACKAGE MENU_FUNCTIONS IS
PROCEDURE Dodaj_Danie(vNazwa Menu.Nazwa_Dania%type, vCena Menu.Cena%type);
PROCEDURE Usun_Danie(vNazwa Menu.Nazwa_Dania%type);
PROCEDURE Zmien_Cene(vNazwa Menu.Nazwa_Dania%type, vNowaCena Menu.Cena%type);
END MENU_FUNCTIONS;
/
CREATE OR REPLACE PACKAGE BODY MENU_FUNCTIONS IS

PROCEDURE Dodaj_Danie(vNazwa Menu.Nazwa_Dania%type, vCena Menu.Cena%type) IS
BEGIN
INSERT INTO Menu (Nazwa_dania,Cena) VALUES (vNazwa, vCena);
END Dodaj_Danie;

PROCEDURE Usun_Danie(vNazwa Menu.Nazwa_Dania%type) IS
BEGIN
DELETE FROM Menu where Nazwa_Dania = vNazwa;
END Usun_Danie;

PROCEDURE Zmien_Cene (vNazwa Menu.Nazwa_Dania%type, vNowaCena Menu.Cena%type) IS
BEGIN
UPDATE Menu
set Cena = vNowaCena where nazwa_dania = vNazwa;
END Zmien_Cene;

END MENU_FUNCTIONS;
/

CREATE OR REPLACE PACKAGE DANIE_NA_ZAMOWIENIU_FUNCTIONS IS
PROCEDURE Dodaj_Danie(vIdRachunku Rachunek.ID_Rachunku%type, vIlosc Danie_na_zamowieniu.ilosc%type,
    vNazwa Danie_na_zamowieniu.Menu_nazwa_dania%type);
PROCEDURE Usun_Danie_Z_Rachunku(vNazwa Menu.Nazwa_Dania%type, vID Rachunek.ID_Rachunku%type);
END;
/

CREATE OR REPLACE PACKAGE BODY DANIE_NA_ZAMOWIENIU_FUNCTIONS IS
    PROCEDURE Dodaj_Danie(vIdRachunku Rachunek.ID_Rachunku%type, 
    vIlosc Danie_na_zamowieniu.ilosc%type,
    vNazwa Danie_na_zamowieniu.Menu_nazwa_dania%type) IS
        BEGIN
            INSERT INTO danie_na_zamowieniu VALUES (vIdRachunku, vIlosc, vNazwa);
        END;
    PROCEDURE Usun_Danie_Z_Rachunku(vNazwa Menu.Nazwa_Dania%type, 
    vID Rachunek.ID_Rachunku%type) IS
        BEGIN
            DELETE FROM DANIE_NA_ZAMOWIENIU
            WHERE menu_nazwa_dania = vNazwa AND rachunek_id_rachunku = vID;
        END;
END DANIE_NA_ZAMOWIENIU_FUNCTIONS;
/


create or replace package rachunek_functions is
    function sumaryczna_cena(vIdRach Rachunek.id_rachunku%type) return float;
    procedure oplac_rachunek(vIdRach Rachunek.id_rachunku%type, vOcena Number);
    procedure usun_rachunek(vIdRach Rachunek.id_rachunku%type);
end;
/

create or replace package body rachunek_functions is

    procedure oplac_rachunek(vIdRach Rachunek.id_rachunku%type, vOcena Number) is
    begin
        update rachunek set ocena = vOcena, oplacono = 'TRUE' where id_rachunku = vIdRach;
    end oplac_rachunek;
    
    function sumaryczna_cena(vIdRach Rachunek.id_rachunku%type) return float
    is
    suma Number(10,2) default 0;
    cursor c is select * from danie_na_zamowieniu where vIdRach = rachunek_id_rachunku;
    vCena Menu.cena%type;
    begin
        for x in c loop
            select cena into vCena from Menu where nazwa_dania = x.menu_nazwa_dania;
            suma := suma + x.ilosc * vCena;
        end loop;
        return suma;
    end sumaryczna_cena;
    
    procedure usun_rachunek(vIdRach Rachunek.id_rachunku%type) is
    begin
        delete from rachunek where vIdRach = id_rachunku;
    end usun_rachunek;
end rachunek_functions; 
/


create or replace package kelner_functions is
    procedure utworz_rachunek(vNrStolika Rachunek.nr_stolika%type, vIdPrac Pracownik.id_prac%type);
    function policz_srednia_ocen(vIdPrac Pracownik.id_prac%type) return number;
--    function otwarte_rachunki(vIdPrac Pracownik.id_prac%type) return natural;
end;
/

create or replace package body kelner_functions is

    procedure utworz_rachunek(vNrStolika Rachunek.nr_stolika%type, vIdPrac Pracownik.id_prac%type) is
    begin
        insert into rachunek values(id_rachunku_seq.nextval, null, 0, vNrStolika, vIdPrac, CURRENT_TIMESTAMP, 'F');
    end;
    
    function policz_srednia_ocen(vIdPrac Pracownik.id_prac%type) return number is
    vSrednia NUMBER := 0;
    begin
        select avg(ocena) into vSrednia from rachunek where id_pracownika = vIdPrac group by id_pracownika;
        return vSrednia;
    end;
    
--    function otwarte_rachunki(vIdPrac Pracownik.id_prac%type) return natural is
--    cursor c is select * from rachunek where oplacono = 'T';
--    begin
--        return c;
--    end;
end;
/
create or replace package menedzer_functions is
--    procedure utworz_rachunek(vNrStolika Rachunek.nr_stolika%type, vIdPrac Pracownik.id_prac%type);
--    function policz_srednia_ocen(vIdPrac Pracownik.id_prac%type) return number;
--    function otwarte_rachunki(vIdPrac Pracownik.id_prac%type) return natural;
    procedure zamow_towar(vIdMenedzera Pracownik.id_prac%type, vId_zam Zamowiony_towar.id_zamowienia%type);
    procedure odbierz_towar(vIdZamowienia  Zamowiony_towar.id_zamowienia%type);
    procedure dodaj_pracownika(vImie Pracownik.imie%type, vNazwisko Pracownik.nazwisko%type, vData Pracownik.data_zatrudnienia%type,
            vCzyKelner Pracownik.czy_Kelner%type, vCzyMenedzer Pracownik.czy_Menedzer%type, vCzyKucharz Pracownik.czy_Kucharz%type);
    procedure usun_pracownika(vIdPrac Pracownik.id_prac%type);
    procedure modyfikuj_pracownika(vId Pracownik.id_prac%type, vImie Pracownik.imie%type, vNazwisko Pracownik.nazwisko%type, vData Pracownik.data_zatrudnienia%type,
            vCzyKelner Pracownik.czy_Kelner%type, vCzyMenedzer Pracownik.czy_Menedzer%type, vCzyKucharz Pracownik.czy_Kucharz%type);
    procedure dodaj_godziny(vIdPrac Pracownik.id_prac%type,  vStawka Pracownik_na_zmianie.stawka%type,
            vStanowisko Pracownik_na_zmianie.pracownik_na_zmianie_id%type, vGodziny Pracownik_na_zmianie.ilosc_godzin%type default 8, vData Pracownik_na_zmianie.data%type default current_date);
end;
/

create or replace package body menedzer_functions is 
 /*   procedure utworz_rachunek(vNrStolika Rachunek.nr_stolika%type, vIdPrac Pracownik.id_prac%type) is
    begin
        insert into rachunek values(id_rachunku_seq.nextval, null, 0, vNrStolika, vIdPrac, CURRENT_TIMESTAMP, 'F');
    end;
    
    function policz_srednia_ocen(vIdPrac Pracownik.id_prac%type) return number is
    vSrednia NUMBER := 0;
    begin
        select avg(ocena) into vSrednia from rachunek where id_pracownika = vIdPrac group by id_pracownika;
        return vSrednia;
    end;
*/    
     procedure zamow_towar(vIdMenedzera Pracownik.id_prac%type, vId_zam zamowiony_towar.id_zamowienia%type) is
     begin
        insert into zamowiony_towar values(vIdMenedzera, vId_zam, 'F');
     end;
     
    procedure odbierz_towar(vIdZamowienia  Zamowiony_towar.id_zamowienia%type) is
    cursor c is select * from towar_na_zamowieniu where towar_id_rachunku = vIdZamowienia;
    begin
        update zamowiony_towar set czy_dostarczony = 'T' where id_zamowienia = vIdZamowienia;
        for x in c loop
            update magazyn m
            set m.ilosc = m.ilosc + x.ilosc 
            where m.nazwa_towaru = x.nazwa_towaru;
        end loop;
    end;
    
    procedure dodaj_pracownika(vImie Pracownik.imie%type, vNazwisko Pracownik.nazwisko%type, vData Pracownik.data_zatrudnienia%type,
            vCzyKelner Pracownik.czy_Kelner%type, vCzyMenedzer Pracownik.czy_Menedzer%type, vCzyKucharz Pracownik.czy_Kucharz%type) is
    begin
        insert into pracownik values(id_prac_seq.nextval, vImie, vNazwisko, vData, vCzyKelner, vCzyKucharz, vCzyMenedzer);
    end;
    
    procedure usun_pracownika(vIdPrac Pracownik.id_prac%type) is
    begin
        delete from pracownik where id_prac = vIdPrac;
    end;

    procedure modyfikuj_pracownika(vId Pracownik.id_prac%type, vImie Pracownik.imie%type, vNazwisko Pracownik.nazwisko%type, vData Pracownik.data_zatrudnienia%type,
            vCzyKelner Pracownik.czy_Kelner%type, vCzyMenedzer Pracownik.czy_Menedzer%type, vCzyKucharz Pracownik.czy_Kucharz%type) is
    begin
        update pracownik set
        imie = vImie,
        nazwisko = vNazwisko,
        data_zatrudnienia = vData,
        czy_kelner = vCzyKelner,
        czy_menedzer = vCzyMenedzer,
        czy_kucharz = vCzyKucharz
        where id_prac = vId;
    end;

    procedure dodaj_godziny(vIdPrac Pracownik.id_prac%type,  vStawka Pracownik_na_zmianie.stawka%type,
            vStanowisko Pracownik_na_zmianie.pracownik_na_zmianie_id%type, vGodziny Pracownik_na_zmianie.ilosc_godzin%type default 8, vData Pracownik_na_zmianie.data%type default current_date) is
    begin 
        insert into pracownik_na_zmianie values(vidPrac, vData, vGodziny, vStawka, vStanowisko);
    end;

end;
/

create or replace package magazyn_functions is
    procedure dodaj_towar(vNazwa Magazyn.nazwa_towaru%type, vIlosc Magazyn.ilosc%type);
    procedure usun_towar(vNazwa Magazyn.nazwa_towaru%type, vIlosc Magazyn.ilosc%type);
end;
/

create or replace package body magazyn_functions is

    LICZ NUMBER;
    procedure dodaj_towar(vNazwa Magazyn.nazwa_towaru%type, vIlosc Magazyn.ilosc%type) is

    BEGIN
        SELECT COUNT (vNazwa) INTO LICZ FROM 
        MAGAZYN WHERE nazwa_towaru = vNazwa;
        
        IF LICZ = 1 THEN
            UPDATE MAGAZYN
            SET ILOSC = ILOSC + vIlosc
            WHERE nazwa_towaru = vNazwa;
        ELSE
            INSERT INTO magazyn(NAZWA_TOWARU,ILOSC)VALUES (vNazwa,vIlosc);
        END IF;
    END;
    
    procedure usun_towar(vNazwa Magazyn.nazwa_towaru%type, vIlosc Magazyn.ilosc%type) is
    begin
        update magazyn set ilosc = ilosc - vIlosc where vNazwa = nazwa_towaru;
    end;

end;
/

CREATE OR REPLACE FUNCTION Moje_Godziny(vIdPrac Pracownik.id_prac%type)
    RETURN INTEGER IS vSuma INTEGER;
BEGIN
    SELECT SUM(ILOSC_GODZIN) INTO vSuma  FROM pracownik_na_zmianie WHERE
    PRACOWNIK_NA_ZMIANIE.DATA BETWEEN CURRENT_DATE  AND (select add_months(sysdate,-1) from dual);
    Return vSuma;
END;
/
CREATE OR REPLACE PROCEDURE Nadaj_Uprawnienia_Menedzer(vIdPrac Pracownik.id_prac%type) is
BEGIN
    UPDATE PRACOWNIK
    SET Czy_Menedzer = 'T' where id_prac = vIdPrac;
    END;
/
CREATE OR REPLACE PROCEDURE Nadaj_Uprawnienia_Kucharz(vIdPrac Pracownik.id_prac%type) is
BEGIN
    UPDATE PRACOWNIK
    SET Czy_Kucharz= 'T' where id_prac = vIdPrac;
    END;
/
CREATE OR REPLACE PACKAGE Pracownik_na_zmianie_FUNCTIONS IS
    FUNCTION Policz_Wyplate(vIdPrac Pracownik.id_prac%type) return integer;
    PROCEDURE Zmien_Stawke (vIdPrac Pracownik.id_prac%type, vStawka NUMBER);
    
END Pracownik_na_zmianie_FUNCTIONS ;
/
CREATE OR REPLACE PACKAGE BODY Pracownik_na_zmianie_FUNCTIONS IS
    FUNCTION Policz_Wyplate(vIdPrac Pracownik.id_prac%type) return integer is vWyplata float;
    vStawka float;
    vGodziny float;
    BEGIN
        SELECT STAWKA INTO vStawka FROM PRACOWNIK_NA_ZMIANIE WHERE PRACOWNIK_ID_PRAC = vIdPrac;
        vGodziny := Moje_Godziny(vIdPrac);
        vWyplata := vGodziny * vStawka;
        RETURN vWyplata;
    END;

    PROCEDURE Zmien_Stawke (vIdPrac Pracownik.id_prac%type, vStawka NUMBER) IS
    BEGIN
        UPDATE PRACOWNIK_NA_ZMIANIE SET STAWKA = vStawka
        WHERE PRACOWNIK_ID_PRAC = vIdPrac;
    END;

END;









--towar_na_zamowieniu
CREATE TABLE towar_na_zamowieniu (
    towar_id_rachunku   INTEGER NOT NULL,
    ilosc                  SMALLINT NOT NULL,
    nazwa_towaru       VARCHAR2(32)
);

ALTER TABLE towar_na_zamowieniu
    ADD CONSTRAINT towar_na_zamowieniu_menu_fk FOREIGN KEY ( nazwa_towaru )
        REFERENCES magazyn ( nazwa_towaru );
        
ALTER TABLE towar_na_zamowieniu
    ADD CONSTRAINT towar_na_zamowieniu_rach_fk FOREIGN KEY ( towar_id_rachunku )
        REFERENCES zamowiony_towar ( id_zamowienia );


create or replace package towar_na_zamowieniu_functions is 
    procedure dodaj_towar(vIdZamowienia Zamowiony_towar.id_zamowienia%type, vIlosc Towar_na_zamowieniu.ilosc%type, vNazwa towar_na_zamowieniu.nazwa_towaru%type);
    procedure usun_towar_z_zamowienia(vIdZamowienia towar_na_zamowieniu.towar_id_rachunku%type, vNazwa towar_na_zamowieniu.nazwa_towaru%type);
end;
/

create or replace package body towar_na_zamowieniu_functions is
    procedure dodaj_towar(vIdZamowienia Zamowiony_towar.id_zamowienia%type, vIlosc Towar_na_zamowieniu.ilosc%type, vNazwa towar_na_zamowieniu.nazwa_towaru%type) is
    begin
        insert into towar_na_zamowieniu values(vIdZamowienia, vIlosc, vNazwa);
    end;

    procedure usun_towar_z_zamowienia(vIdZamowienia towar_na_zamowieniu.towar_id_rachunku%type, vNazwa towar_na_zamowieniu.nazwa_towaru%type) is
    begin
        delete from towar_na_zamowieniu t where t.nazwa_towaru = vNazwa and towar_id_rachunku = vIdZamowienia; 
    end;

end;
/

alter table zamowiony_towar drop(czy_dostarczony);
alter table zamowiony_towar add(czy_dostarczony VARCHAR2(1));

commit;
select * from menu;

begin
magazyn_functions.dodaj_towar('frytki',7);
end;


select * from pracownik_na_zmianie;

insert into pracownik_na_zmianie values (42, current_date, 10, 25, 1);

commit;