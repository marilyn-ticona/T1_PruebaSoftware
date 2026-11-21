insert into carrera (codigo, nombre) values
('CI', 'Computación e Informática'),
('AE', 'Administración de Empresas');

insert into docente (codigo, nombres, apellidos, email, especialidad) values
('D0001', 'Marco', 'Salazar', 'marco.salazar@cibertec.edu.pe', 'Calidad y pruebas de software'),
('D0002', 'Lucía', 'Paredes', 'lucia.paredes@cibertec.edu.pe', 'Desarrollo de software'),
('D0003', 'Raúl', 'Gómez', 'raul.gomez@cibertec.edu.pe', 'Inteligencia artificial'),
('D0004', 'Elena', 'Castro', 'elena.castro@cibertec.edu.pe', 'Base de datos y seguridad'),
('D0005', 'Pablo', 'Rojas', 'pablo.rojas@cibertec.edu.pe', 'Redes');

insert into curso (codigo, nombre, ciclo, creditos, vacantes, modalidad, dia, hora_inicio, hora_fin, carrera_id, docente_id)
select 'PS5511', 'Pruebas de Software', 6, 3, 30, 'PRESENCIAL', 'LUNES', '08:00', '11:00', k.id, d.id from carrera k, docente d where k.codigo = 'CI' and d.codigo = 'D0001';
insert into curso (codigo, nombre, ciclo, creditos, vacantes, modalidad, dia, hora_inicio, hora_fin, carrera_id, docente_id)
select 'DM5512', 'Desarrollo de Aplicaciones Móviles', 6, 4, 25, 'HIBRIDO', 'MARTES', '08:00', '12:00', k.id, d.id from carrera k, docente d where k.codigo = 'CI' and d.codigo = 'D0002';
insert into curso (codigo, nombre, ciclo, creditos, vacantes, modalidad, dia, hora_inicio, hora_fin, carrera_id, docente_id)
select 'IA5513', 'Inteligencia Artificial Aplicada', 6, 3, 1, 'VIRTUAL', 'MIERCOLES', '08:00', '11:00', k.id, d.id from carrera k, docente d where k.codigo = 'CI' and d.codigo = 'D0003';
insert into curso (codigo, nombre, ciclo, creditos, vacantes, modalidad, dia, hora_inicio, hora_fin, carrera_id, docente_id)
select 'TS5514', 'Taller de Software', 6, 4, 30, 'HIBRIDO', 'JUEVES', '08:00', '12:00', k.id, d.id from carrera k, docente d where k.codigo = 'CI' and d.codigo = 'D0002';
insert into curso (codigo, nombre, ciclo, creditos, vacantes, modalidad, dia, hora_inicio, hora_fin, carrera_id, docente_id)
select 'QA5517', 'Gestión de Calidad de Software', 6, 3, 30, 'VIRTUAL', 'LUNES', '09:00', '12:00', k.id, d.id from carrera k, docente d where k.codigo = 'CI' and d.codigo = 'D0001';
insert into curso (codigo, nombre, ciclo, creditos, vacantes, modalidad, dia, hora_inicio, hora_fin, carrera_id, docente_id)
select 'PI5620', 'Proyecto Integrador', 6, 6, 20, 'PRESENCIAL', 'VIERNES', '08:00', '14:00', k.id, d.id from carrera k, docente d where k.codigo = 'CI' and d.codigo = 'D0001';
insert into curso (codigo, nombre, ciclo, creditos, vacantes, modalidad, dia, hora_inicio, hora_fin, carrera_id, docente_id)
select 'SA5516', 'Seguridad de Aplicaciones', 5, 4, 30, 'VIRTUAL', 'LUNES', '14:00', '18:00', k.id, d.id from carrera k, docente d where k.codigo = 'CI' and d.codigo = 'D0004';
insert into curso (codigo, nombre, ciclo, creditos, vacantes, modalidad, dia, hora_inicio, hora_fin, carrera_id, docente_id)
select 'BD5410', 'Base de Datos Avanzado', 5, 4, 30, 'PRESENCIAL', 'MARTES', '14:00', '18:00', k.id, d.id from carrera k, docente d where k.codigo = 'CI' and d.codigo = 'D0004';
insert into curso (codigo, nombre, ciclo, creditos, vacantes, modalidad, dia, hora_inicio, hora_fin, carrera_id, docente_id)
select 'LP5401', 'Lenguaje de Programación II', 5, 4, 30, 'PRESENCIAL', 'SABADO', '08:00', '12:00', k.id, d.id from carrera k, docente d where k.codigo = 'CI' and d.codigo = 'D0002';
insert into curso (codigo, nombre, ciclo, creditos, vacantes, modalidad, dia, hora_inicio, hora_fin, carrera_id, docente_id)
select 'AS5402', 'Análisis y Diseño de Sistemas', 5, 3, 30, 'VIRTUAL', 'SABADO', '14:00', '17:00', k.id, d.id from carrera k, docente d where k.codigo = 'CI' and d.codigo = 'D0003';
insert into curso (codigo, nombre, ciclo, creditos, vacantes, modalidad, dia, hora_inicio, hora_fin, carrera_id, docente_id)
select 'AD5101', 'Contabilidad General', 1, 3, 35, 'PRESENCIAL', 'MIERCOLES', '14:00', '17:00', k.id, null from carrera k where k.codigo = 'AE';

insert into curso_requisito (curso_id, requisito_id)
select c.id, r.id from curso c, curso r
where (c.codigo, r.codigo) in (('PS5511', 'AS5402'), ('QA5517', 'AS5402'), ('PI5620', 'AS5402'),
                               ('DM5512', 'LP5401'), ('TS5514', 'LP5401'));

insert into alumno (codigo, nombres, apellidos, email, promedio, tiene_deuda, carrera_id)
select x.codigo, x.nombres, x.apellidos, x.email, x.promedio, x.deuda, k.id
from (select 'i202610001' codigo, 'Ana' nombres, 'Torres' apellidos, 'ana.torres@cibertec.edu.pe' email, 18.0 promedio, false deuda, 'CI' carrera
      union all select 'i202610002', 'Luis', 'Ramos', 'luis.ramos@cibertec.edu.pe', 16.0, false, 'CI'
      union all select 'i202610003', 'Carla', 'Quispe', 'carla.quispe@cibertec.edu.pe', 12.0, false, 'CI'
      union all select 'i202610004', 'Jorge', 'Huamán', 'jorge.huaman@cibertec.edu.pe', 14.0, true, 'CI'
      union all select 'i202610005', 'Rosa', 'Medina', 'rosa.medina@cibertec.edu.pe', 15.5, false, 'CI'
      union all select 'i202610006', 'Pedro', 'Salas', 'pedro.salas@cibertec.edu.pe', 13.0, false, 'CI'
      union all select 'i202620001', 'Sofía', 'Vargas', 'sofia.vargas@cibertec.edu.pe', 16.0, false, 'AE') x
join carrera k on k.codigo = x.carrera
order by x.codigo;

insert into periodo (codigo, fecha_inicio, fecha_fin, estado) values
('2026-1', '2026-03-16', '2026-07-11', 'CERRADO'),
('2026-2', '2026-08-17', '2026-12-12', 'ABIERTO');

insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 360.0, 'REGISTRADA', null, current_timestamp, null from alumno a, curso c, periodo p where a.codigo = 'i202610003' and c.codigo = 'IA5513' and p.codigo = '2026-2';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 648.0, 'PAGADA', null, current_timestamp, current_timestamp from alumno a, curso c, periodo p where a.codigo = 'i202610005' and c.codigo = 'PI5620' and p.codigo = '2026-2';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 324.0, 'REGISTRADA', null, current_timestamp, null from alumno a, curso c, periodo p where a.codigo = 'i202610005' and c.codigo = 'PS5511' and p.codigo = '2026-2';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 432.0, 'REGISTRADA', null, current_timestamp, null from alumno a, curso c, periodo p where a.codigo = 'i202610005' and c.codigo = 'DM5512' and p.codigo = '2026-2';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 432.0, 'REGISTRADA', null, current_timestamp, null from alumno a, curso c, periodo p where a.codigo = 'i202610005' and c.codigo = 'SA5516' and p.codigo = '2026-2';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 432.0, 'REGISTRADA', null, current_timestamp, null from alumno a, curso c, periodo p where a.codigo = 'i202610005' and c.codigo = 'BD5410' and p.codigo = '2026-2';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 324.0, 'REGISTRADA', null, current_timestamp, null from alumno a, curso c, periodo p where a.codigo = 'i202610002' and c.codigo = 'PS5511' and p.codigo = '2026-2';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 384.0, 'ANULADA', null, current_timestamp, null from alumno a, curso c, periodo p where a.codigo = 'i202610001' and c.codigo = 'DM5512' and p.codigo = '2026-2';

insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 384.0, 'PAGADA', 18, '2026-03-10 09:00:00', '2026-03-12 09:00:00' from alumno a, curso c, periodo p where a.codigo = 'i202610001' and c.codigo = 'LP5401' and p.codigo = '2026-1';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 288.0, 'PAGADA', 17, '2026-03-10 09:00:00', '2026-03-12 09:00:00' from alumno a, curso c, periodo p where a.codigo = 'i202610001' and c.codigo = 'AS5402' and p.codigo = '2026-1';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 432.0, 'PAGADA', 16, '2026-03-10 09:00:00', '2026-03-12 09:00:00' from alumno a, curso c, periodo p where a.codigo = 'i202610002' and c.codigo = 'LP5401' and p.codigo = '2026-1';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 324.0, 'PAGADA', 15, '2026-03-10 09:00:00', '2026-03-12 09:00:00' from alumno a, curso c, periodo p where a.codigo = 'i202610002' and c.codigo = 'AS5402' and p.codigo = '2026-1';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 480.0, 'PAGADA', 13, '2026-03-10 09:00:00', '2026-03-12 09:00:00' from alumno a, curso c, periodo p where a.codigo = 'i202610003' and c.codigo = 'LP5401' and p.codigo = '2026-1';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 360.0, 'PAGADA', 14, '2026-03-10 09:00:00', '2026-03-12 09:00:00' from alumno a, curso c, periodo p where a.codigo = 'i202610003' and c.codigo = 'AS5402' and p.codigo = '2026-1';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 480.0, 'PAGADA', 11, '2026-03-10 09:00:00', '2026-03-12 09:00:00' from alumno a, curso c, periodo p where a.codigo = 'i202610004' and c.codigo = 'LP5401' and p.codigo = '2026-1';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 360.0, 'PAGADA', 13, '2026-03-10 09:00:00', '2026-03-12 09:00:00' from alumno a, curso c, periodo p where a.codigo = 'i202610004' and c.codigo = 'AS5402' and p.codigo = '2026-1';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 432.0, 'PAGADA', 15, '2026-03-10 09:00:00', '2026-03-12 09:00:00' from alumno a, curso c, periodo p where a.codigo = 'i202610005' and c.codigo = 'LP5401' and p.codigo = '2026-1';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 324.0, 'PAGADA', 16, '2026-03-10 09:00:00', '2026-03-12 09:00:00' from alumno a, curso c, periodo p where a.codigo = 'i202610005' and c.codigo = 'AS5402' and p.codigo = '2026-1';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 480.0, 'PAGADA', 14, '2026-03-10 09:00:00', '2026-03-12 09:00:00' from alumno a, curso c, periodo p where a.codigo = 'i202610006' and c.codigo = 'LP5401' and p.codigo = '2026-1';
insert into matricula (alumno_id, curso_id, periodo_id, monto, estado, nota, fecha_registro, fecha_pago)
select a.id, c.id, p.id, 360.0, 'PAGADA', 9, '2026-03-10 09:00:00', '2026-03-12 09:00:00' from alumno a, curso c, periodo p where a.codigo = 'i202610006' and c.codigo = 'AS5402' and p.codigo = '2026-1';

insert into pago (matricula_id, monto, metodo, fecha)
select m.id, m.monto, case when m.id = 2 then 'TARJETA' else 'YAPE' end, m.fecha_pago
from matricula m where m.estado = 'PAGADA' order by m.id;

insert into usuario (username, password, rol, intentos_fallidos) values
('admin', '$2a$10$aB/LwlTNDOJp5Gp3OZTrT.MQJvn5nwpV9vj32NiVk0DisuMW8l.Be', 'ADMIN', 0);
insert into usuario (username, password, rol, intentos_fallidos)
select codigo, '$2a$10$le7xyxUUdd15NXs3I5ltDuNhLUK5yP4ExDAfOAA53gqpRFGJFg3j.', 'DOCENTE', 0 from docente order by codigo;
insert into usuario (username, password, rol, intentos_fallidos)
select codigo, '$2a$10$le7xyxUUdd15NXs3I5ltDuNhLUK5yP4ExDAfOAA53gqpRFGJFg3j.', 'ALUMNO', 0 from alumno order by codigo;
