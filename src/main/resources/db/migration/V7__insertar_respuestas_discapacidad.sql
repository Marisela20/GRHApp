-- V8__insertar_respuestas_discapacidad.sql
-- Codificación: UTF-8 sin BOM

-- Pregunta 1: Municipio principal
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1001, 'Sabaneta', 1),
                                                                     (1001, 'Medellín', 2),
                                                                     (1001, 'Envigado', 3),
                                                                     (1001, 'La Estrella', 4),
                                                                     (1001, 'Bello', 5),
                                                                     (1001, 'Otro', 0);

-- Pregunta 2: Tamaño de la organización
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1002, 'Menos de 10 empleados', 1),
                                                                     (1002, 'Entre 11 y 50 empleados', 2),
                                                                     (1002, 'Entre 51 y 200 empleados', 3),
                                                                     (1002, 'Más de 200 empleados', 4);

-- Pregunta 3: Sector
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1003, 'Servicios (consultoría, finanzas, salud…)', 1),
                                                                     (1003, 'Industria (manufactura, construcción...)', 2),
                                                                     (1003, 'Tecnología (software, hardware…)', 3),
                                                                     (1003, 'Comercio (minorista, mayorista…)', 4),
                                                                     (1003, 'Otro', 0);

-- Pregunta 5: Política formal de inclusión
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1005, 'Sí, está documentada y es regularmente comunicada', 4),
                                                                     (1005, 'Sí, pero no es socializada', 3),
                                                                     (1005, 'Se encuentra en desarrollo', 2),
                                                                     (1005, 'No', 1);

-- Pregunta 6: Responsable de inclusión
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1006, 'Un área o departamento específico (Recursos Humanos)', 4),
                                                                     (1006, 'Un comité con funciones específicas en inclusión laboral', 3),
                                                                     (1006, 'La alta dirección y/o gerencia', 2),
                                                                     (1006, 'No hay un responsable', 1);

-- Pregunta 7: Formación sobre inclusión
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1007, 'Sí, regularmente', 4),
                                                                     (1007, 'Sí, ocasionalmente', 3),
                                                                     (1007, 'Sí, pero solo para algunos miembros de la organización', 2),
                                                                     (1007, 'No', 1);

-- Pregunta 8: Recursos para adaptaciones
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1008, 'Presupuesto para ajustes razonables', 4),
                                                                     (1008, 'Consultores o asesores para la gestión de adaptaciones', 3),
                                                                     (1008, 'Equipo interno con conocimientos', 2),
                                                                     (1008, 'No se dispone de recursos', 1);

-- Pregunta 9: Disponibilidad para ajustes
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1009, 'Sí, estamos completamente abiertos a hacer los ajustes necesarios', 4),
                                                                     (1009, 'En general, sí, pero dependerá de los ajustes específicos requeridos', 3),
                                                                     (1009, 'Necesito más información sobre los ajustes necesarios', 2),
                                                                     (1009, 'Sí, pero los ajustes podrían requerir tiempo y recursos adicionales', 2),
                                                                     (1009, 'No, no podemos realizar ajustes en este momento', 1);

-- Pregunta 10: Tipos de apoyos disponibles
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1010, 'Asesoramiento y mentoría para el desarrollo del trabajo', 4),
                                                                     (1010, 'Ajuste de horarios de trabajo', 3),
                                                                     (1010, 'Material de trabajo adecuado de acuerdo con su discapacidad', 3),
                                                                     (1010, 'Puesto de trabajo de fácil acceso a cafetín, servicios sanitarios y áreas comunes', 3),
                                                                     (1010, 'Flexibilidad en horarios y tareas', 2),
                                                                     (1010, 'Ninguno', 1),
                                                                     (1010, 'Otro', 0);
-- Pregunta 11: Frecuencia de evaluaciones
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1011, 'Semestralmente', 4),
                                                                     (1011, 'Anualmente', 3),
                                                                     (1011, 'Solo cuando se introducen cambios', 2),
                                                                     (1011, 'Nunca', 1);

-- Pregunta 12: Inclusión en cultura organizacional
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1012, 'Campañas de sensibilización y eventos', 4),
                                                                     (1012, 'Informes de Responsabilidad Social', 3),
                                                                     (1012, 'Otro', 2),
                                                                     (1012, 'No hay iniciativas visibles', 1);

-- Pregunta 13: Nivel de participación
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1013, 'Alta participación', 4),
                                                                     (1013, 'Participación moderada', 3),
                                                                     (1013, 'Baja participación', 2),
                                                                     (1013, 'La organización no consolida esta información', 1);

-- Pregunta 14: Teletrabajo como adaptación
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1014, 'Sí, para todos los empleados con discapacidad', 4),
                                                                     (1014, 'Sí, en casos específicos según la necesidad individual', 3),
                                                                     (1014, 'No, la organización no ofrece teletrabajo como adaptación', 2),
                                                                     (1014, 'No, sin embargo, se ha considerado implementar esta opción', 1);

-- Pregunta 15: Conocimiento sobre normatividad
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1015, 'La organización cuenta con un equipo especializado que asegura el cumplimiento de la normativa', 4),
                                                                     (1015, 'La organización tiene un conocimiento básico de la normativa', 2),
                                                                     (1015, 'La organización no evalúa la normatividad aplicable a personas con discapacidad', 1);

-- Pregunta 16: Cultura de inclusividad
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1016, 'Programas de sensibilización (Implementación de capacitaciones y talleres)', 4),
                                                                     (1016, 'Eventos y actividades inclusivas', 3),
                                                                     (1016, 'Información visual (Avisos y señalizaciones)', 2),
                                                                     (1016, 'Otro', 2),
                                                                     (1016, 'No se han implementado iniciativas', 1);

-- Pregunta 17: Equidad en contratación
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1017, 'Provisión de ajustes razonables', 4),
                                                                     (1017, 'Capacitación en inclusión y sesgos', 3),
                                                                     (1017, 'Políticas y prácticas inclusivas', 3),
                                                                     (1017, 'Asesoría y apoyo especializado', 2);

-- Pregunta 18: Adaptaciones físicas
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1018, 'Rampas de acceso', 4),
                                                                     (1018, 'Ascensores', 4),
                                                                     (1018, 'Puertas automáticas', 3),
                                                                     (1018, 'Baños adaptados', 4),
                                                                     (1018, 'Otro', 2),
                                                                     (1018, 'No se han realizado adaptaciones', 1);

-- Pregunta 19: Modificaciones para movilidad reducida
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1019, 'Espacios accesibles', 4),
                                                                     (1019, 'Equipo de trabajo orientado a la función adaptado a la condición de la persona', 4),
                                                                     (1019, 'Herramientas y equipos adaptados a la condición de la persona', 3),
                                                                     (1019, 'Procedimientos de trabajo adaptados a la condición de la persona', 3),
                                                                     (1019, 'Otro', 2),
                                                                     (1019, 'No se han realizado modificaciones', 1);

-- Pregunta 20: Tecnologías para discapacidad auditiva
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1020, 'Sistemas de alarma visual', 4),
                                                                     (1020, 'Dispositivos de amplificación de sonido', 4),
                                                                     (1020, 'Sistemas de comunicación por texto', 3),
                                                                     (1020, 'Subtitulado o interpretaciones en videos', 3),
                                                                     (1020, 'Otro', 2),
                                                                     (1020, 'No se han implementado tecnologías', 1);
-- Pregunta 21: Ajustes en diseño de puestos para discapacidad auditiva
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1021, 'Sistemas de comunicación accesibles', 4),
                                                                     (1021, 'Espacios de trabajo que no impliquen escucha y el habla', 3),
                                                                     (1021, 'Señalización visual', 3),
                                                                     (1021, 'Horarios flexibles', 2),
                                                                     (1021, 'Otro', 2),
                                                                     (1021, 'No se han realizado ajustes', 1);

-- Pregunta 22: Adaptaciones en comunicación interna (auditiva)
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1022, 'Subtitulado en videos', 4),
                                                                     (1022, 'Comunicaciones escritas y claras', 4),
                                                                     (1022, 'Sistemas de alertas visuales', 3),
                                                                     (1022, 'Otro', 2),
                                                                     (1022, 'No se han implementado medidas', 1);

-- Pregunta 23: Acciones de sensibilización sobre discapacidad auditiva
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1023, 'Capacitación y talleres en Lengua de Señas', 4),
                                                                     (1023, 'Recursos educativos para sensibilizar a las personas sobre la diferencia', 3),
                                                                     (1023, 'Programas de inclusión y apoyo', 3),
                                                                     (1023, 'Otro', 2),
                                                                     (1023, 'No se han implementado acciones', 1);

-- Pregunta 24: Tecnologías asistidas para discapacidad visual
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1024, 'Lectores de pantalla', 4),
                                                                     (1024, 'Impresoras en braille', 4),
                                                                     (1024, 'Magnificadores de texto', 3),
                                                                     (1024, 'Documentos en formato accesible (como texto grande)', 3),
                                                                     (1024, 'Otro', 2),
                                                                     (1024, 'No se han implementado tecnologías específicas', 1);

-- Pregunta 25: Adaptaciones en puestos de trabajo (visual)
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1025, 'Herramientas de lectura y escritura accesibles', 4),
                                                                     (1025, 'Iluminación adecuada', 3),
                                                                     (1025, 'Software de lectura de pantalla', 4),
                                                                     (1025, 'Horarios flexibles', 2),
                                                                     (1025, 'Etiquetas en braille o formatos grandes', 3),
                                                                     (1025, 'No se han realizado adaptaciones', 1);

-- Pregunta 26: Formatos accesibles para información (visual)
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1026, 'Texto en braille', 4),
                                                                     (1026, 'Audio', 4),
                                                                     (1026, 'Documentos en formato de texto grande', 3),
                                                                     (1026, 'Información en formatos digitales accesibles', 3),
                                                                     (1026, 'Otro', 2),
                                                                     (1026, 'No se han utilizado formatos accesibles', 1);

-- Pregunta 27: Herramientas para discapacidad intelectual
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1027, 'Software de apoyo cognitivo', 4),
                                                                     (1027, 'Tareas simplificadas', 3),
                                                                     (1027, 'Horarios flexibles', 2),
                                                                     (1027, 'Guías o manuales con instrucciones claras', 3),
                                                                     (1027, 'Acompañamiento de un profesional', 4),
                                                                     (1027, 'Otro', 2),
                                                                     (1027, 'No se han proporcionado apoyos', 1);

-- Pregunta 28: Modificaciones en el puesto (intelectual)
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1028, 'Tareas simplificadas', 3),
                                                                     (1028, 'Instrucciones claras y visuales', 3),
                                                                     (1028, 'Apoyo adicional en la formación', 3),
                                                                     (1028, 'Acompañamiento de un profesional', 4),
                                                                     (1028, 'Otro', 2),
                                                                     (1028, 'No se han realizado modificaciones', 1);

-- Pregunta 29: Adaptación de información y políticas (intelectual)
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1029, 'Instrucciones claras y simplificadas', 3),
                                                                     (1029, 'Apoyo visual y herramientas de ayuda', 3),
                                                                     (1029, 'Formación adaptada y programas de apoyo', 4),
                                                                     (1029, 'Otro', 2),
                                                                     (1029, 'No se han realizado adaptaciones', 1);

-- Pregunta 30: Programas de apoyo para discapacidad intelectual
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1030, 'Programas de desarrollo profesional adaptados', 4),
                                                                     (1030, 'Apoyo en la formación y el trabajo', 3),
                                                                     (1030, 'Ajustes en los procesos de trabajo', 3),
                                                                     (1030, 'Otro', 2),
                                                                     (1030, 'No se han implementado programas', 1);

-- Pregunta 31: Herramientas para discapacidad psicosocial
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1031, 'Teletrabajo', 4),
                                                                     (1031, 'Horarios flexibles', 3),
                                                                     (1031, 'Acceso a terapia psicológica o consejería', 4),
                                                                     (1031, 'Talleres de manejo del estrés y habilidades sociales', 3),
                                                                     (1031, 'Otro', 2),
                                                                     (1031, 'No se han proporcionado apoyos', 1);

-- Pregunta 32: Modificaciones para discapacidad psicosocial
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1032, 'Tareas simplificadas', 3),
                                                                     (1032, 'Modificación del entorno físico (espacios tranquilos, iluminación adecuada)', 4),
                                                                     (1032, 'Instrucciones claras', 3),
                                                                     (1032, 'Grupos de apoyo', 3),
                                                                     (1032, 'Acompañamiento de un profesional', 4),
                                                                     (1032, 'Otro', 2),
                                                                     (1032, 'No se han realizado modificaciones', 1);

-- Pregunta 33: Adaptación de políticas para discapacidad psicosocial
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES                                 (1033, 'Guías para la gestión de actividades dentro de la empresa', 4),
                                                                     (1033, 'Boletines relacionados con estrategias para el cuidado de la salud mental', 3),
                                                                     (1033, 'Otro', 2),
                                                                     (1033, 'No se han realizado adaptaciones', 1);

-- Pregunta 34: Programas de apoyo para discapacidad psicosocial
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES                       (1034, 'Formación en primeros auxilios psicológicos para la organización', 4),
                                                                     (1034, 'Programas de salud mental y derechos humanos en la empresa', 4),
                                                                     (1034, 'Formación para gerentes y colegas sobre discapacidad psicosocial', 3),
                                                                     (1034, 'Creación de un comité de diversidad e inclusión', 3),
                                                                     (1034, 'Promoción de un ambiente de confianza', 3),
                                                                     (1034, 'Otro', 2),
                                                                     (1034, 'No se han implementado programas', 1);

-- Pregunta 35: Herramientas para discapacidad múltiple
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1035, 'Software de apoyo cognitivo', 4),
                                                                     (1035, 'Tareas simplificadas', 3),
                                                                     (1035, 'Horarios flexibles', 2),
                                                                     (1035, 'Guías o manuales con instrucciones', 3),
                                                                     (1035, 'Acompañamiento de un profesional', 4),
                                                                     (1035, 'Tecnología de asistencia', 4),
                                                                     (1035, 'Modificaciones en el entorno físico', 3),
                                                                     (1035, 'Programas de salud mental', 3),
                                                                     (1035, 'Flexibilidad en tareas', 2),
                                                                     (1035, 'Otro', 2),
                                                                     (1035, 'No se han proporcionado apoyos', 1);

-- Pregunta 36: Modificaciones para discapacidad múltiple
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1036, 'Instrucciones claras y visuales', 3),
                                                                     (1036, 'Apoyo adicional en la formación', 3),
                                                                     (1036, 'Acompañamiento de un profesional', 4),
                                                                     (1036, 'Ajustes en la comunicación', 3),
                                                                     (1036, 'Tareas por fases', 2),
                                                                     (1036, 'Supervisión constante', 3),
                                                                     (1036, 'Entornos de trabajo adaptativos', 3),
                                                                     (1036, 'Otro', 2),
                                                                     (1036, 'No se han realizado modificaciones', 1);

-- Pregunta 37: Adaptación de políticas para discapacidad múltiple
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1037, 'Materiales formativos accesibles', 4),
                                                                     (1037, 'Comunicaciones periódicas', 3),
                                                                     (1037, 'Espacios de feedback', 3),
                                                                     (1037, 'Otro', 2),
                                                                     (1037, 'No se han realizado modificaciones', 1);

-- Pregunta 38: Programas de apoyo para discapacidad múltiple
INSERT INTO discapacity_answer_option (question_id, text, score) VALUES
                                                                     (1038, 'Programas de desarrollo profesional adaptados', 4),
                                                                     (1038, 'Mentoría', 3),
                                                                     (1038, 'Asesoría', 3),
                                                                     (1038, 'Otro', 2),
                                                                     (1038, 'No se han implementado programas', 1);
