insert into t_category (id,description)values(1001,'Comic books');
insert into t_category (id,description)values(1002,'Movies');
insert into t_category (id,description)values(1003,'books');
insert into t_supplier (id,name)values(1001,'Panini Comics');
insert into t_supplier (id,name)values(1002,'Amazon');
insert into t_product (id,name,fk_category,fk_supplier,quantity_available,created_at )values(1,'Crise nas Infiniteas terras', 1001,1001,5, CURRENT_TIMESTAMP);
insert into t_product (id,name,fk_category,fk_supplier,quantity_available,created_at )values(2,'Interestelar', 1002,1002,5,CURRENT_TIMESTAMP);
insert into t_product (id,name,fk_category,fk_supplier,quantity_available,created_at )values(3,'Harry Potter e a Pedra Filosofal',1003,1002,3,CURRENT_TIMESTAMP);