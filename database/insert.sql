--artikal kolona opis, pakovanje tipa decimal
--za prvi artikal prosle godine uradi par prometnih dokumenata i apar stavki u analitici

insert into Preduzece values ('ITM', 12345, 011486781, 'Fridriha Engelsa 54', 'Beograd')
insert into Preduzece values ('TCM', 24587, 011789421, 'Carlija Caplina 1', 'Beograd')
insert into Preduzece values ('MMM', 74582, 011459474, 'Karla Marksa 16', 'Beograd')
/*
insert into [Poslovni partner] values(24587)
insert into [Poslovni partner] values(74582)
*/
insert into Sektor values(12345, 'Fridriha Engelsa 54', 'Sektor 1')
insert into Sektor values(24587, 'Kumanovska 3', 'Sektor 1'),(24587, 'Sumatovacka 142', 'Sektor 2')
insert into Sektor values(74582, 'Savska 73', 'Sektor 1')

insert into Magacin values(1, 'Magacin 1'),(1, 'Magacin 2')
insert into Magacin values(2, 'Magacin 1'),(3, 'Magacin 1'),(4, 'Magacin 1')

insert into [Grupa artikala] values('Konditorski proizvodi'),('Bezalkoholna pica'),('Alkoholna pica'),('Mlecni proizvodi'),('Gotova jela i mesne preradjevine')

insert into Artikal values(0.250, 'kg', 'Cokoladne napolitanke', 1, null),(2, 'l', 'Sok od narandze', 2, null), (0.100, 'kg', 'Cokolada sa lesnicima', 1, null),(0.250, 'kg', 'Integralni keks', 1, null),
						  (2, 'l', 'Sok od jabuke', 2, null), (0.75, 'l', 'Crno vino', 3, null), (0.33, 'l', 'Tamno pivo', 3, null), (0.05, 'kg', 'Krompirov cips', 1, null),
						  (0.75, 'l', 'Belo vino', 3, null), (0.75, 'l', 'Roze', 3, null), (0.33, 'l', 'Apple cider', 3, null), (2, 'l', 'Mineralna voda', 2, null),
						  (0.50, 'l', 'Energetsko pice', 2, null),(1, 'l', 'Kravlje mleko 2.8% mm', 4, null),(0.2, 'l', 'Kisela pavlaka', 4, null), (0.5, 'l', 'Vocni jogurt', 4, null),
						  (0.05, 'kg', 'Jetrena pasteta', 5, null),(1, 'kg', 'Cajna kobasica', 5, null), (0.5, 'kg', 'Pasulj sa slaninom', 5, null), (0.25, 'l', 'Cokoladno mleko', 4,null),
						  (0.25, 'kg', 'Mesni narezak', 5, null)

insert into [Poslovna godina] values(2016, 12345, 1),(2017, 12345, 0), (2017, 24587, 0), (2017, 74582, 0)
						  
insert into [Magacinska kartica] values(1,110, 95, 0, 110, 0, 0, 0, 0, 2, 1, 4275, 45,0 ), (1, 95, 89, 105, 95, 0, 0, 0, 0, 2, 2, 16910, 190, 0), (1, 110, 100, 123, 110, 0, 0, 0, 0, 2, 3, 34000, 340, 0),
									   (1, 86, 83, 0, 86, 0, 0, 0, 0, 2, 4, 2988 ,36, 0), (1, 74, 68, 80, 74, 0, 0, 0, 0, 2, 5, 3060 ,45, 0), (1, 170, 155, 210, 170, 0, 0, 0, 0, 2, 6, 16275  ,105, 0),
									   (2, 78, 70, 0, 78, 0, 0, 0, 0, 2, 7, 7000, 100, 0), (2, 40, 35, 47, 40, 0, 0, 0, 0, 2, 8, 4200 ,120, 0), (2, 165, 148, 185, 165, 0, 0, 0, 0, 2, 9, 9916 ,67, 0),
									   (2, 175, 145, 180, 175, 0, 0, 0, 0, 2, 10, 14790 ,102, 0),
									   (3, 115, 105, 0, 115, 0, 0, 0, 0, 3, 11, 4200 ,40, 0), (3, 75, 63, 0, 75, 0, 0, 0, 0, 3, 12, 3150 ,50, 0), (3, 68, 57, 75, 68, 0, 0, 0, 0, 3, 13, 5700 ,100, 0 ),
									   (3, 80, 60, 0, 80, 0, 0, 0, 0, 3, 14, 3000 ,50, 0),
									   (4, 40, 25, 0, 40, 0, 0, 0, 0, 3, 15, 1125 ,45, 0), (4, 55, 40, 0, 55, 0, 0, 0, 0, 3, 16, 2800 ,70, 0), (4, 65, 55, 0, 65, 0, 0, 0, 0, 3, 20, 1650 ,30, 0),
									   (5, 45, 35, 0, 45, 0, 0, 0, 0, 4, 17, 1120 ,32, 0), (5, 823, 700, 0, 823, 0, 0, 0, 0, 4, 18, 21000 ,30, 0),(5, 250, 220, 0, 250, 0, 0, 0, 0, 4, 19, 9900, 45, 0 ),
									   (5, 150, 120, 0, 150, 0, 0, 0, 0, 4, 21, 12360 ,103, 0),
									   (1, 105, 95, 0, 105, 0, 0, 28, 2940, 1, 1, 4725 ,45, 0)
									   
									   

insert into [Prometni dokument] values (1, '2017-01-17', '2017-01-17', 2, 1, NULL, 3),(1, '2017-01-14', '2017-01-17', 2, 1, NULL, 3),(1, '2017-01-15', '2017-01-17', 2, 1, NULL, 3)

insert into [Stavka prometnog dokumenta] values (1, 5, 105, 525, 1),(2, 15, 105, 1575, 1),(3, 8, 105, 840, 1)

insert into [Analitika magacinske kartice] values (1, 3, 5, 525, 'I', 22, 1),(2, 3, 15, 1575, 'I', 22, 1),(3, 3, 8, 840, 'I', 22, 1)