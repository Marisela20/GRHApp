INSERT IGNORE INTO role(name) VALUES ('ADMIN'),('ORGANIZATION'),('USER');
INSERT INTO classification (id,description) VALUES (1,'DIMENSION'),(6,'CULTURA'),(17,'RESULTADOS'),(18,'EXTERNAL'),(19,'INTERNAL'),(20,'STABILITY'),(21,'CHANGE');

INSERT INTO classification (id,description,total_points) VALUES (2,'PLANEACION',72),(3,'HABILIDADES',90),(4,'MOTIVACION',90),(5,'OPORTUNIDAD',90),
(7,'NIVEL DEL TRABAJADOR',57),(8,'NIVEL ORGANIZACIONAL',57), (9,'TECNOLOGIA',57),(10,'SOSTENIBILIDAD GVRH',57),(11,'GRH COMPARTIDA',57),(12,'GRH INTERNACIONAL',57),
(13,'INDICE PARTICIPACION',15),(14,'INDICE COHERENCIA',15),(15,'INDICE ADAPTABILIDAD',15),(16,'INDICE MISION',15);

INSERT INTO conclusions (id,classification_id,description,min_value, max_value) VALUES
(1,2,'Uno de los factores de éxito de la estrategia de la empresa está asociado a su articulación con las prácticas de GRH. El primer paso para esta articulación es la planeación de GRH y el diseño organizacional.',0,20),
(2,2,'Las prácticas de planeación y diseño organizacional de tu empresa se encuentran en un nivel inicial de desarrollo. Se realiza una GRH empírica y la estructura de las actividades son elementos instrumentales. Se recomienda pensar sobre la forma de implementar herramientas y estrategias de planeación fundamentadas en el los objetivos de las personas y la organización.',21,40),
(3,2,'Un nivel intermedio en este componente demuestra que la GRH se ha estado esforzando en mejorar sus prácticas de planeación y diseño organizacional. Aun se puede mejorar más, convirtiendo este aspecto en fuente de ventaja competitiva y diferenciación, a través del uso de herramientas que los lleven a un nivel global y predictivo de los resultados.',41,59),
(4,2,'Para la empresa la articulación de la planeación de GRH y el diseño organizacional son fundamentales para la construcción de valor a las partes interesadas. Los trabajadores y la Organización están alcanzando sus propósitos compartidos. Sigan adelante fortaleciendo la estrategia de la empresa desde la GRH.',60,72),
(5,3,'Las organizaciones que basan su ventaja competitiva en las capacidades de sus trabajadores serán mucho más eficientes y productivas. La empresa puede aumentar su aporte para potenciar las habilidades de sus trabajadores a través de la mejora en la selección, vinculación, formación y desarrollo humano.',6,25),
(6,3,'Estar en un nivel inicial en las habilidades denota que la empresa tiende a establecer sus acciones para la contratación y el desarrollo de las competencias de los trabajadores de manera instrumental. Hay una preocupación limitada al desempeño de las tareas y del marco normativo. Para pasar a un siguiente nivel deben impulsar practicas de colaboración y alineación de objetivos individuales y colectivos.',26,50),
(7,3,'La empresa ya se encuentra en un nivel medio en la contratación y desarrollo de las habilidades de los trabajadores, con orientación hacia la solución presente de las situaciones empresariales. Se utilizan prácticas y actividades de adaptación al entorno, comprendiendo la responsabilidad sobre la generación de valor de la organización y las personas a las partes interesadas.',51,74),
(8,3,'Se ha identificado que gran parte de la ventaja competitiva de la empresa está soportada en las capacidades de las personas, siendo evidente en el enfoque por la atracción y desarrollo de las habilidades de los trabajadores. La empresa y los trabajadores se han preocupado por el desarrollo de habilidades individuales y colectivas.',75,90),
(9,4,'La motivación de los trabajadores permite aumentar los niveles de compromiso y por consiguiente de productividad colectivos. En esta empresa la GRH puede mejorar sus prácticas para facilitar el logro de sus objetivos individuales, grupales y organizacionales.',2,25),
(10,4,'Aún la organización se encuentra en un punto de inicio en su aporte a la satisfacción de las personas. Probablemente las remuneraciones y otras condiciones asociadas al desarrollo humano se limitan a las normas legales y carecen de una articulación con los objetivos empresariales.',26,50),
(11,4,'Las acciones para motivar a los trabajadores ya se encuentran en un nivel intermedio. Esto demuestra una preocupación por mejorar la satisfacción de las personas para que cumplan con sus responsabilidades. Se entiende que los trabajadores tienen  unas necesidades y se aporta a su solución de forma parcial. Podemos trabajar más en el conocimiento de las personas para diseñar estrategias alineadas a sus necesidades particulares.',51,74),
(12,4,'La motivación de los trabajadores de la empresa está basada en un auténtico sentido de responsabilidad por el desarrollo de las personas, considerando las diferentes esferas que hacen parte de la existencia del ser humano, yendo más allá de lo meramente instrumental. Continúen aportando valor desde el cuidado del ser humano.',75,90),
(13,5,'La creación de oportunidades de crecimiento individual como práctica sistemática de la organización puede llevar al desarrollo y crecimiento organizacional. Tu empresa puede aumentar su nivel de GRH y competitividad al contar con oportunidades para sus trabajadores, equipos de trabajo y procesos.',0,25),
(14,5,'Al encontrarse en un nivel inicial en el componente de oportunidad, se cuenta con herramientas limitadas al cumplimiento de las tareas diarias, descuidando en cierta medida las posibilidades de generación de valor compartido. Se cuenta con instrumentos básicos para lograr resultados diferentes a los que ya se han obtenido.',26,50),
(15,5,'En el nivel intermedio, las oportunidades se empiezan a evidenciar y por lo tanto a realizar prácticas para gestionar las oportunidades tanto para las personas como para la empresa. Ya se evidencia una necesidad de gestionar hacia un futuro próximo, logrando que se articulen en alguna medida los objetivos individuales y colectivos.',51,74),
(16,5,'Las prácticas de GRH permiten a los trabajadores y a la Organización como colectivo identificar y aprovechar las oportunidades para crecer hacia el mejoramiento y la innovación permanentes. Se aplica la gestión del conocimiento y la innovación como oportunidad de mejora continua basada en las capacidades y recursos intangibles.',75,90),
(17,7,'Las prácticas de GRH se encuentran en un nivel sin desarrollo. Se sugiere trabajar en la orientación al cuidado del trabajador y la satisfacción de sus necesidades de manera que se aumente su nivel de compromiso.',4,15),
(18,7,'En el nivel inicial, las prácticas de GRH tienen en cuenta al individuo como un recurso con la obligación de aportar su tiempo para cumplir con las tareas asignadas de forma individual y atendiendo a la operatividad de las indicaciones de una dirección burocrática y altamente jerarquizada. Para pasar a un siguiente nivel se sugiere tener mayor confianza en las capacidades de las personas.',16,32),
(19,7,'Las prácticas de GRH de nivel intermedio nos plantean que la organización ya empieza a reconocer que los trabajadores tienen unas necesidades y se crean programas aplicables de forma general para los colaboradores en sus diferentes niveles. Aún se puede trabajar más para reconocer la diversidad de las necesidades de las personas.',33,47),
(20,7,'El nivel de madurez de las prácticas de GRH en tu empresa demuestran un claro compromiso con los trabajadores, resultando en la protección de sus condiciones de calidad de vida. La organización conoce las ventajas que tiene la preocupación por los objetivos de los individuos, convirtiéndose en una clara ventaja competitiva para la estrategia organizacional.',48,57),
(21,8,'La GRH en este momento no le está generando valor a la organización, manteniendo prácticas que limitan su desarrollo. Se recomienda que empiece a diseñar y ejecutar prácticas que se orienten a los resultados empresariales.',4,15),
(22,8,'Las prácticas de GRH para el nivel organizacional en un estado inicial denotan que la empresa se enfoca principalmente en el cumplimiento de la normatividad y marco legal. Se ha dejado a la GRH únicamente la responsabilidad de dar cumplimiento a lo mínimo establecido y por lo tanto no se evidencia el valor que puede ser aportado a los objetivos empresariales.',16,32),
(23,8,'En un nivel intermedio, la organización empieza a recibir beneficios de la GRH, en cuanto se evidencia que realiza un aporte al desempeño de las personas y por lo tanto hay un acercamiento a los objetivos colectivos. Aún se puede mejorar el alcance estratégico de la GRH en beneficio de los objetivos y valores compartidos.',33,47),
(24,8,'Se ha logrado la articulación de los objetivos individuales y colectivos de los trabajadores con los objetivos estratégicos de la organización, generando prácticas de GRH que se orientan hacia el futuro del negocio. Las personas se comprometen con los propósitos organizacionales.',48,57),
(25,9,'Las prácticas de GRH se pueden optimizar utilizando herramientas tecnológicas de apoyo. Se sugiere revisar plataformas compartidas o de licencia abierta para mejorar los resultados.',0,15),
(26,9,'Aunque ya se empiezan a utilizar herramientas sencillas para apoyar la GRH, sus actividades se pueden optimizar y mejorar con plataformas especializadas. Las herramientas ofimáticas son necesarias hoy día, sin embargo, la operatividad está sujeta a la intervención de sus usuarios lo que puede aumentar el riesgo de tener errores por lo general involuntarios.',16,32),
(27,9,'Ubicarse en la posición intermedia con relación a las herramientas tecnológicas nos lleva a identificar que ya se cuenta con algunas plataformas especializadas que facilitan la operación de la GRH. Se mantiene la oportunidad de utilizar herramientas que faciliten además la toma de decisiones orientadas a la predicción de prácticas en beneficio de los trabajadores y la empresa de forma simultánea.',33,47),
(28,9,'Las tecnologías de información y comunicación disponibles en la empresa representan la orientación hacia la innovación, contando así con procesos eficientes, inmersos en una lógica de mejora continua. Las tecnologías son utilizadas para tomar decisiones predictivas basadas en la analítica que permiten realizar acciones en el presente que soportan las metas en el futuro. ',48,57),
(29,10,'Hoy día la ventaja competitiva se mantiene agregando valor a los diferentes grupos de interés. Se puede indagar la forma de implementar prácticas sociales y/o ambientales que no afecten los resultados económicos.',0,15),
(30,10,'En el nivel inicial se observa que la empresa empieza a ser consciente de su impacto sobre el entorno, especialmente en los ámbitos social y ambiental, aunque sus acciones tienen un enfoque principalmente en los beneficios financieros.',16,32),
(31,10,'En una posición intermedia, se empiezan a legitimar las acciones de responsabilidad social que puede realizar la empresa hacia los diferentes actores del entorno. El propósito de sus acciones trasciende los objetivos financieros y ya aporta soluciones en beneficio de diferentes partes interesadas.',33,47),
(32,10,'Las prácticas de GRH de la empresa demuestran un legítimo valor sobre los impactos sociales y ambientales de las relaciones organizacionales con sus trabajadores y de ellos con su entorno. Se resalta el empoderamiento desde la GRH de las personas para llevar una cultura orientada hacia la sostenibilidad en sus áreas de influencia. ',48,57),
(33,11,'Las organizaciones que confían en los aportes de las personas para diseñar y mejorar sus procesos aumentan considerablemente su desempeño. Es hora de dejar que las personas participen en las soluciones de GRH.',0,15),
(34,11,'Las acciones y políticas de GRH se encuentran en un nivel inicial de desarrollo, siendo establecidas de forma centralizada por decisiones directivas. Es posible contar con una mayor participación de las personas de acuerdo con sus roles y así mejorar su impacto en la comunidad empresarial.',16,32),
(35,11,'La empresa ya se encuentra en una posición intermedia sobre las prácticas de GRH compartidas y éticas, demostrando que se empieza a aumentar la confianza por las capacidades de los trabajadores. Se logran consensos y articulación de los objetivos individuales y colectivos. Aún se pueden incluir aspectos basados en la ética de valores compartidos para tomar decisiones de GRH.',33,47),
(36,11,'Se ha evidenciado en la empresa un claro compromiso por mantener unos valores compartidos entre ella y sus trabajadores. Las personas participan abiertamente, plasmando sus intereses junto con los intereses organizacionales, manteniendo una ética basada en la transparencia y en el valor compartido.',48,57),
(37,12,'Aunque no tengan en este momento actividades internacionales, es importante considerar que tus competidores directos e indirectos tienen alcance global. Se pueden definir prácticas que apoyen los niveles de competitividad global.',0,15),
(38,12,'Las prácticas de GRH están definidas considerando principalmente aspectos internos de la organización. En este nivel inicial aún no se ha identificado la necesidad de contemplar las condiciones del mercado para lograr una ventaja competitiva para la organización en una economía primero nacional y luego global.',16,32),
(39,12,'En el nivel intermedio, la organización es consciente de su participación en el mercado y sus estrategias de GRH empiezan a responder a los elementos de un mercado nacional para desarrollar una ventaja competitiva. Se recomienda observar también lineamientos macro para determinar un nivel de competitividad y de ventajas competitivas a nivel internacional, comprendiendo las dinámicas globales del entorno.',33,47),
(40,12,'Las prácticas de GRH ofrecen soporte a los niveles de competitividad global de la empresa. Su gestión permite que las personas comprendan y cuenten con las capacidades necesarias para identificar nuevos mercados. Igualmente se incentivan las habilidades para detectar posibles oportunidades y amenazas en las condiciones del entorno a nivel macro en lo político, social, ambiental, tecnológico, económico y legal.',48,57),
(41,17,'En conclusión, tu organización tiene excelentes oportunidades de mejora en cuanto a las prácticas de GRH, encontrándose en el nivel más básico de desarrollo. Recuerda que puedes apoyarte de las mejores prácticas de GRH para ir avanzando de nivel en el crecimiento de la empresa y el desarrollo de tus trabajadores.',8,79),
(42,17,'En conclusión, la organización se encuentra en un nivel inicial en el desarrollo de sus prácticas de GRH. En principio, altamente instrumental y basado en prácticas tradicionales, que pueden ser soportadas por el énfasis en lo global, mejorando los apoyos tecnológicos.',80,199),
(43,17,'En conclusión, la empresa tiene prácticas de GRH en un nivel de desarrollo intermedio, teniendo en cuenta que algunas de ellas aportan a la estrategia del negocio y a las necesidades de las personas. Se recomienda revisar en detalle cada uno de los componentes de este instrumento con el fin de diseñar nuevas alternativas que generen valor especialmente en el largo plazo  con alcance a las diferentes partes interesadas. ',200,284),
(44,17,'En conclusión, la Empresa se encuentra en un nivel alto de madurez en cuanto a las prácticas de GRH, considerando el impacto que se tiene sobre todas las partes interesadas. La GRH tiene proyecciones hacia el futuro alineadas con la prospectiva estratégica de la empresa para lo cual se utilizan diversas herramientas y técnicas que cubren lo operativo, estratégico y analítico para soportar un modelo de toma de decisiones predictivo, sin descuidar el presente.',285,342),
(45,13,'Está dado por el empoderamiento, la orientación al equipo y el desarrollo de capacidades. En tu organización la cultura presenta un bajo nivel de participación, por lo tanto, las decisiones tienden a ser altamente centralizadas. La GRH debe promover la alineación y Engagement de los trabajadores, como una gran oportunidad.',3,7),
(46,13,'Los niveles de participación de la comunidad de la empresa se encuentran a nivel intermedio. Si este es el enfoque y necesidad para los objetivos colectivos, es un elemento que se puede mantener. ',8,11),
(47,13,'La cultura organizacional cuenta con el más alto nivel en cuanto a la participación de los individuos y grupos de trabajo. Se promueve el desarrollo de las capacidades de las personas. La cooperación es parte de la vida en la empresa. Las personas son tenidas en cuenta y sus aportes son importantes.',12,15),
(48,14,'Se puede definir por la coordinación e integración, el acuerdo y los valores centrales. En esta dimensión se evidencia que el sistema, la estructura y los procesos están en un nivel inicial, por lo tanto, se sugiere que la empresa fortalezca estos componentes de gestión para apalancar los resultados de la estrategia.',3,7),
(49,14,'La coherencia presenta un nivel intermedio, que comprendiendo la ética y principios de las estructuras organizacionales de hoy, es posible que deba intervenirse para pasar a una siguiente posición. De todas maneras esto dependerá de las necesidades estratégicas y estructurales empresariales.',8,11),
(50,14,'Las personas en la empresa tienen un conjunto de valores compartidos que guían sus comportamientos y formas de hacer las cosas. Se comparte una perspectiva común de la razón de ser y propósito organizacional hacia el cual se orientan los esfuerzos colectivos.',12,15),
(52,15,'Esta compuesto por los niveles de aprendizaje organizacional, el enfoque al cliente y la creación del cambio. A través de este índice se observa que la empresa tiene un bajo nivel de adaptabilidad, por lo tanto se recomienda que realice un análisis más profundo del contexto externo de los negocios y el entorno del mercado e implemente estrategias para su adaptación.',3,7),
(53,15,'La organización cuenta con niveles intermedios en su valor por la adaptabilidad y flexibilidad. Es recomendable revisar si esta posición resulta como la más pertinente para la empresa y sus partes interesadas.',8,11),
(54,15,'Se cuenta con una clara adaptabilidad del negocio hacia las condiciones internas y externas. Se incentivan comportamientos hacia la mejora de los procesos. Se realizan esfuerzos orientados a comprender las necesidades del mercado y de los clientes. ',12,15),
(55,16,'Se conoce por la dirección e intención estratégica, las metas y objetivos y la visión. Un bajo nivel en este índice muestra una oportunidad para socializar y motivar a las partes interesadas sobre el propósito empresarial, dejando una claridad acerca de la dirección que se debe tomar como colectivo.',3,7),
(56,16,'Tenemos un reconocimiento de la misión y propósito central de nivel medio. Este aspecto vale la pena fortalecerlo de manera que se incentive una mayor alineación de los comportamientos de los miembros de la organización con los objetivos organizacionales.',8,11),
(57,16,'Se tiene completa claridad de la razón de ser del negocio y todos dentro de la organización se esfuerzan para alcanzar el propósito colectivo. Individuos y equipos de trabajo hacen lo que esté a su alcance para mantener el éxito organizacional en el largo plazo sin descuidar el presente y el corto plazo.',12,15),
(58,18,'La empresa carece de una orientación hacia lo externo, por lo tanto, se sugiere revisar si es coherente con su propósito empresarial.',6,15),
(59,18,'La orientación de la cultura hacia el contexto externo se encuentra en un nivel intermedio. Bajo este panorama, las personas y la misma empresa gestionan sus actividades basados parcialmente en elementos del entorno.',16,24),
(60,18,'Hay una alta orientación hacia lo externo en la empresa. Las personas entienden lo que pasa en el entorno para tomar decisiones que vinculen tengan impactos positivos en todas las partes interesadas.',25,30),
(61,19,'La empresa carece de una orientación hacia lo interno, por lo tanto, se sugiere revisar si es coherente con su propósito empresarial.',6,15),
(62,19,'La orientación hacia los elementos internos de la empresa están enmarcados por un nivel intermedio. De esta forma, se identifica que las actuaciones de las personas y el logro de los objetivos se tienen en cuenta de forma parcial.',16,24),
(63,19,'La orientación del trabajo hacia lo interno permite la identificación y desarrollo de capacidades que faciliten la mejora continua y la satisfacción de las personas en todos sus niveles.',25,30),
(64,20,'La empresa tiene una perspectiva de estabilidad en el nivel mínimo, por lo tanto, se sugiere revisar si es coherente con su propósito empresarial.',6,15),
(65,20,'La organización y los trabajadores se orientan hacia niveles intermedios de estabilidad, por lo que las historias y tradiciones llevan a comportamientos que busquen de alguna manera ser transformados a través de nuevos comportamientos de las personas.',16,24),
(66,20,'La organización conserva los valores y tradiciones de su historia y gestiona sus recursos con este fundamento. Es posible que se cuente con una preocupación por los resultados financieros y los indicadores tangibles. ',25,30),
(67,21,'La empresa tiene una perspectiva de cambio en el nivel mínimo, por lo tanto, se sugiere revisar si es coherente con su propósito empresarial.',6,15),
(68,21,'Se cuenta con una orientación intermedia hacia la flexibilidad y el cambio. Por lo tanto, es posible que las iniciativas de creatividad y mejora, así como ciertas decisiones necesiten algunos procesos antes de ser implementados.',16,24),
(69,21,'Se presentan niveles representativos de flexibilidad y de orientación al cambio, con lo que, la organización puede admitir niveles apropiados de creatividad, pensando en la satisfacción de los clientes.',25,30);


INSERT INTO survey_item (id,description,classification_id, is_question, section, is_sublevel) VALUES
(1,'¿Cuál de las siguientes opciones define mejor el uso de las tecnologías para la Gestión de Recursos Humanos de su organización?:',1,1,1,0),
(2,'¿Su organización está comprometida con el cuidado de la sociedad y/o del medio ambiente?',1,1,1,0),
(3,'¿En su organización cómo se definen las acciones y estrategias de GRH?',1,1,1,0),
(4,'¿Su organización está preparada para realizar operaciones de comercio internacional?',1,1,1,0),

(5,'¿Cómo se puede describir mejor la estructura de la organización?',2,1,1,0),
(6,'¿Cuál es el grado de complejidad jerárquica de la estructura organizacional?',2,1,1,0),
(7,'¿Su organización ha definido un plan anual de la plantilla de trabajadores?',2,1,1,0),
(8,'¿El plan anual de su organización incluye un presupuesto de costos laborales?',2,1,1,0),
(9,'¿Su organización tiene descrito y analiza constantemente los cargos de trabajo?',2,1,1,0),

(10,'¿Cómo se lleva a cabo, en su organización el reclutamiento de los trabajadores?',3,1,1,0),
(11,'Las técnicas de selección utilizadas para cubrir las vacantes de los cargos de la organización:',3,1,1,0),
(12,'¿Qué tipo de contratos laborales utiliza principalmente la organización para formalizar las relaciones de trabajo?',3,1,1,0),
(13,'En la organización, el proceso de adaptación de personal se caracteriza por:',3,1,1,0),
(14,'¿En la organización existen procesos y programas de capacitación, formación y entrenamiento a los trabajadores?',3,1,1,0),

(15,'¿En la organización, se realiza un  proceso de evaluación de desempeño?',4,1,1,0),
(16,'¿La organización cuenta con políticas salariales y de compensación?',4,1,1,0),
(17,'¿La organización tiene definidos beneficios no constitutivos de salario?',4,1,1,0),
(18,'¿En la organización se ha definido un proceso para el crecimiento y desarrollo de los trabajadores?',4,1,1,0),
(19,'¿La organización cumple con los estándares mínimos del SG-SST?',4,1,1,0),

(20,'¿La organización tiene definida una política de flexibilidad laboral que garantice los derechos de los colaboradores?',5,1,1,0),
(21,'¿La organización promueve el trabajo colaborativo?',5,1,1,0),
(22,'¿En la organización los colaboradores participan en la toma de decisiones agregando valor a la organización y sus grupos de interés?',5,1,1,0),
(23,'¿En la organización se desarrolla el trabajo incentivando el intercambio de información entre los diferentes niveles y equipos de trabajo?s',5,1,1,0),
(24,'¿En la organización se plantean estrategias de gestión de conocimiento para el proceso de mejora?',5,1,1,0);


INSERT INTO survey_item (id,description,classification_id,father_id, is_question, section, is_sublevel) VALUES
(29,'La GRH de la organización utiliza herramientas tecnológicas para aumentar su desempeño.',1,1,0,1,0),
(30,'La GRH de la organización No apoya sus procesos con el uso de herramientas tecnológicas.',1,1,0,1,0),

(31,'La organización cuenta con estrategias de responsabiidad social empresarial y/o sostenibilidad social y ambiental.',1,2,0,1,0),
(32,'La organización no cuenta con políticas ni estrategias de RSE ni sostenibilidad social y ambiental.',1,2,0,1,0),

(33,'Las estrategias de GRH son definidas tomando en cuenta la participación y características de la dirección y los trabajadores.',1,3,0,1,0),
(34,'Las estrategias de GRH son definidas de manera centralizada y autocrática.',1,3,0,1,0),

(35,'La empresa tiene actividades de comercio internacional o estas se tienen incluidas en la planeación estratégica.',1,4,0,1,0),
(36,'No se cuenta con actividades internacionales ni tampoco se ha realizado un plan formal orientado a ellas.',1,4,0,1,0),

(37,'La estructura organizacional está definida por departamentos y/o funciones.',2,5,0,1,0),
(38,'La organización cuenta con un mapa de procesos.',2,5,0,1,0),
(39,'La organización trabaja basada en proyectos.',2,5,0,1,0),
(40,'Se cuenta con redes de trabajo colaborativas.',2,5,0,1,0),
(41,'Se cuenta unicamente con una estructura informal por personas / roles.',2,5,0,1,0),

(42,'Alto grado de jerarquización.',2,6,0,1,0),
(43,'Bajo grado de jerarquización.',2,6,0,1,0),

(44,'Se realiza un plan estructurado anual de la plantilla de trabajadores.',2,7,0,1,0),
(45,'No se realiza un plan estructurado anual de la plantilla de trabajadores',2,7,0,1,0),

(46,'Se realiza un presupuesto anual de los costos laborales y de GRH.',2,8,0,1,0),
(47,'No se realiza un presupuesto anual de costos laborales y de GRH.',2,8,0,1,0),

(48,'Se mantiene actualizado de forma permanente por la GRH.',2,9,0,1,0),
(49,'No se realiza un análisis y descripción de cargos o se encuentra desactualizado.',2,9,0,1,0),

(50,'Directamente por la organización.',3,10,0,1,0),
(51,'Se terceriza mediante la contratación de expertos.',3,10,0,1,0),

(52,'Son contratos a término indefinido.',3,12,0,1,0),
(53,'Son contratos con duración definida a término fijo o por duración de la obra.',3,12,0,1,0),
(54,'Se define el tipo de contrato según el cargo.',3,12,0,1,0),

(55,'Se realiza la inducción y acogida a todos los trabajadores que ingresan por primera vez.',3,13,0,1,0),
(56,'Existen restricciones para realizar adecuadamente un proceso de inducción y acogida.',3,13,0,1,0),

(57,'Se realizan procesos y programas de formación a los trabajadores de forma recurrente.',3,14,0,1,0),
(58,'Generalmente No se realizan actividades de formación, capacitación y entrenamiento.',3,14,0,1,0),

(59,'Se realiza periodicamente una evaluación del desempeño de los trabajadores.',4,15,0,1,0),
(60,'No se realiza un proceso de evaluación y gestión del desempeño de los trabajadores.',4,15,0,1,0),

(61,'La organización cuenta con una política y estructura de compensación salarial claramente definida.',4,16,0,1,0),
(62,'La organización no ha definido una política ni estructura de compensación salarial.',4,16,0,1,0),

(63,'Se cuenta con condiciones claras para asignar los planes de beneficios y compensaciones no salariales.',4,17,0,1,0),
(64,'No se ha definido ningún tipo de beneficio no salarial para los trabajadores.',4,17,0,1,0),

(65,'Existe un proceso para permitir a los trabajadores un crecimiento y desarrollo en la organización.',4,18,0,1,0),
(66,'No hay definido un programa sistemático de desarrollo del trabajador.',4,18,0,1,0),

(67,'El SG-SST cumple con los estándares mínimos acorde con lo establecido en la normatividad.',4,19,0,1,0),
(68,'El SG-SST cumple parcialmente con los estándares mínimos acorde con lo establecido en la normatividad.',4,19,0,1,0),
(69,'El SG-SST no cumple con los estándares mínimos acorde con lo establecido en la normatividad.',4,19,0,1,0),

(70,'Se ha definido un programa de flexibilidad en el trabajo.',5,20,0,1,0),
(71,'No se tiene definido un programa de flexibilidad laboral.',5,20,0,1,0),

(72,'El trabajo en la organización es principalmente en equipos y grupos colaborativos.',5,21,0,1,0),
(73,'El trabajo es principalmente individual.',5,21,0,1,0),

(74,'Los trabajadores intervienen en la toma de decisiones e iniciativas de innovación y mejora.',5,22,0,1,0),
(75,'Las decisiones en la empresa son tomadas generalmente por los directivos.',5,22,0,1,0),

(76,'La información derivada de la operación de la empresa es almacenada y transferida para el uso en toda la organización.',5,23,0,1,0),
(77,'La información de la operación de la organización es de predominancia altamente confidencial y se restringe su intercambio interno y externo.',5,23,0,1,0),

(78,'La gestión del conocimiento se ha convertido en una estrategia clave del negocio.',5,24,0,1,0),
(79,'La organización No cuenta con una estrategia definida de gestión del conocimiento.',5,24,0,1,0);

INSERT INTO survey_item (id,description,classification_id, is_question, section, is_sublevel, score) VALUES
(80,'Cumplir los objetivos y desempeño del trabajador.',7,0,1,1,1),
(81,'La satisfacción de las necesidades básicas de los trabajadores de forma general.',7,0,1,1,2),
(82,'La satisfacción de las necesidades básicas y superiores de los trabajadores teniendo en cuenta la diversidad e inclusión.',7,0,1,1,3),

(83,'Responder al marco legal y normativo.',8,0,1,1,1),
(84,'Mejorar la productividad de los equipos de trabajo.',8,0,1,1,2),
(85,'Contribuir al cumplimiento de los objetivos estratégicos.',8,0,1,1,3),

(86,'Herramientas ofimáticas (P. Ej: Word, Excel, Power point).',9,0,1,1,1),
(87,'Software especializado en Gestión de Recursos Humanos.',9,0,1,1,2),
(88,'Herramientas avanzadas de big data y analítica del talento, optimizando las actividades y decisiones.',9,0,1,1,3),
(89,'No se utiliza ninguna herramienta tecnológica',9,0,1,1,0),

(90,'Lograr los objetivos financieros de la organización.',10,0,1,1,1),
(91,'Potenciar las acciones de responsabilidad social de la empresa (RSE).',10,0,1,1,2),
(92,'Empoderar  a los empleados sobre su responsabilidad social y ambiental desde sus roles en la organización.',10,0,1,1,3),

(93,'Responder a los lineamientos del empresario y los directivos.',11,0,1,1,1),
(94,'La participación del empresario y los empleados.',11,0,1,1,2),
(95,'La participación y valores éticos compartidos por empresario y empleados.',11,0,1,1,3),

(96,'Aspectos internos propios de la organización. ',12,0,1,1,1),
(97,'El desarrollo de capacidades para generar ventajas competitivas nacionales.',12,0,1,1,2),
(98,'El desarrollo de capacidades para generar ventajas competitivas nacionales e internacionales.',12,0,1,1,3);

INSERT INTO survey_item (`id`, `description`) VALUES ('999', 'Empty');

INSERT INTO survey_item (id,description,classification_id, is_question, section, is_sublevel) VALUES
(99,'Todos piensan que su trabajo es importante y tiene un impacto positivo en los resultados.',13,1,2,0),
(100,'La cooperación entre las diferentes partes de la organización es alentada activamente.',13,1,2,0),
(101,'Las capacidades de la gente son vistas como una importante fuente de ventaja competitiva.',13,1,2,0),
(102,'Personas de diferentes partes de la organización comparten una perspectiva común.',14,1,2,0),
(103,'Hay un claro acuerdo acerca de la manera correcta y la incorrecta de hacer las cosas.',14,1,2,0),
(104,'Hay un claro y consistente conjunto de valores que determinan la manera como se hacen negocios.',14,1,2,0),
(105,'La innovación y la toma de riesgos son alentados y reconocidos.',15,1,2,0),
(106,'Todos los trabajadores tienen un profundo entendimiento de las necesidades y deseos del cliente.',15,1,2,0),
(107,'Nuevas y mejores formas de hacer las cosas son continuamente adoptadas.',15,1,2,0),
(108,'Hay una clara misión que da significado y dirección al trabajo de todos los miembros de la organización.',16,1,2,0),
(109,'La gente entiende lo que necesita hacer para mantener el éxito en el largo plazo.',16,1,2,0),
(110,'Estamos abiertos para cumplir las demandas de corto plazo sin poner en peligro la visión a largo plazo.',16,1,2,0);

INSERT INTO survey_item (id,description,classification_id, is_question, section, is_sublevel, score) VALUES
(111,'Totalmente en desacuerdo',16,0,2,0,1),
(112,'En desacuerdo',16,0,2,0,2),
(113,'Ni de acuerdo ni en desacuerdo',16,0,2,0,3),
(114,'De acuerdo',16,0,2,0,4),
(115,'Totalmente de acuerdo',16,0,2,0,5);



INSERT INTO survey_item_excluded (question_id,answer_id, classification_id) VALUES (1,30,9),(2,32,10), (3,34,11),(4,36,12);
INSERT INTO survey_item_excluded (question_id,answer_id) VALUES (5,41),(6,42),(6,43),(7,45),(8,47),(9,49),(13,56),
(14,58),(15,60),(17,64),(18,66),(19,69),(20,71),(21,73),(22,75),(23,77),(24,79);

INSERT INTO level_position (id,description) VALUES (1,'Operativo'),(2,'Administrativo sin personal a cargo'),(3,'Mando medio con personal a cargo'), (4,'Gerencia / Dirección'),
(5,'Presidencia '),(6,'Junta directiva'),(7,'Docente'),(8,'Otros');

INSERT INTO economic_area (id, description) VALUES (1,'Agrícola'),(2,'Industrial'),(3,'Servicios'),(4,'Organización híbrida / BIC / Empresa B / Cuarto sector'),
(5,'Entidad pública'),(6,'Entidad sin ánimo de lucro'),(7,'Institución de educación');

INSERT INTO user (name, username, password,role_id) VALUES ('Admin', 'admin.ceipa@gmail.com', '$2a$10$wlIBCivgFLfjHJIkTccEmOJjjsSRv5vvJuxYPXw9pVBl2quUPB/Iq', 'ADMIN');

INSERT INTO survey_item (id,description,classification_id,father_id, is_question, section, is_sublevel) VALUES
(116,'La estructura organizacional desde la perspectiva del trabajador está orientada a:',7,5,1,1,1),
(117,'La estructura organizacional desde la perspectiva empresarial está enfocada en:',8,5,1,1,1),
(118,'La estructura organizacional se gestiona y mantiene actualizada utilizando principalmente:',9,5,1,1,1),
(119,'La estructura organizacional desde la perspectiva de sostenibilidad se orienta hacia:',10,5,1,1,1),
(120,'La estructura organizacional se ha definido de tal manera que permita:',11,5,1,1,1),
(121,'El diseño de la estructura organizacional está principalmente orientado a:',12,5,1,1,1),

(122,'El plan anual de la plantilla de trabajadores desde la perspectiva del trabajador busca:',7,7,1,1,1),
(123,'El plan anual de la plantilla de trabajadores se ha definido para:',8,7,1,1,1),
(124,'La planeación de la plantilla de trabajadores necesarios se realiza con el apoyo de:',9,7,1,1,1),
(125,'La plantilla de trabajadores de la Organización se define anualmente pensando en:',10,7,1,1,1),
(126,'La planeación de la plantilla de trabajadores se realiza permitiendo la participación de las personas de forma que se logre:',11,7,1,1,1),
(127,'La planeación anual de la plantilla de trabajadores tiene en cuenta:',12,7,1,1,1),

(128,'El presupuesto anual de costos laborales incluye inversiones para:',7,8,1,1,1),
(129,'El presupuesto anual de costos laborales se realiza periódicamente con el fin de:',8,8,1,1,1),
(130,'El presupuesto periódico de costos laborales se realiza utilizando principalmente:',9,8,1,1,1),
(131,'El presupuesto de costos laborales de la empresa está enfocado en:',10,8,1,1,1),
(132,'El presupuesto de costos laborales se define periódicamente de manera que la gestión de recursos humanos se oriente a:',11,8,1,1,1),
(133,'El presupuesto de costos laborales permite que la organización y los trabajadores se enfoquen en:',12,8,1,1,1),

(134,'El análisis y descripción de  los cargos respecto al trabajador permiten:',7,9,1,1,1),
(135,'Desde la perspectiva de la organización, el análisis y descripción de cargos se orienta a:',8,9,1,1,1),
(136,'El análisis y descripción de los cargos se sistematiza utilizando registros soportados en:',9,9,1,1,1),
(137,'En el análisis y descripción de cargos se han incluido componentes orientados a:',10,9,1,1,1),
(138,'El análisis y descripción de los cargos incluye lineamientos de participación de los trabajadores, que dan lugar a:',11,9,1,1,1),
(139,'La descripción de los cargos de la organización incluye:',12,9,1,1,1),

(140,'El reclutamiento de los trabajadores se realiza pensando en:',7,10,1,1,1),
(141,'Para la organización, el reclutamiento de los trabajadores busca:',8,10,1,1,1),
(142,'Las convocatorias de empleos se realizan en medios apoyados por:',9,10,1,1,1),
(143,'El proceso de reclutamiento incluye condiciones para:',10,10,1,1,1),
(144,'El reclutamiento de los trabajadores permite la atracción de personas, permitiendo:',11,10,1,1,1),
(145,'El reclutamiento de los trabajadores incluye requerimientos que se orienten a:',12,10,1,1,1),

(146,'Las técnicas de selección de nuevos trabajadores están enfocadas en identificar de qué manera se logra:',7,11,1,1,1),
(147,'Las técnicas de selección utilizadas por la organización le ayudan a:',8,11,1,1,1),
(148,'El proceso y técnicas de selección tienen como apoyo:',9,11,1,1,1),
(149,'Las técnicas de selección con respecto a la sostenibilidad, buscan:',10,11,1,1,1),
(150,'Las técnicas de selección utilizadas por la organización permiten identificar si es posible:',11,11,1,1,1),
(151,'Las técnicas de selección utilizadas por la organización permiten evidenciar si los candidatos cuentan con:',12,11,1,1,1),

(152,'Los contratos laborales en su organización buscan:',7,12,1,1,1),
(153,'Los contratos laborales de la organización formalizan relaciones laborales que buscan:',8,12,1,1,1),
(154,'Los contratos laborales se dejan registrados mediante el uso de:',9,12,1,1,1),
(155,'Los contratos laborales incluyen responsabilidades de los trabajadores para:',10,12,1,1,1),
(156,'La formalización de los contratos de trabajo ayudan a los trabajadores y a la organización:',11,12,1,1,1),
(157,'Los contratos laborales con los trabajadores presentan condiciones relacionados con:',12,12,1,1,1),

(158,'El objetivo de la inducción y acogida de los trabajadores a la organización está orientado a:',7,13,1,1,1),
(159,'El proceso de inducción y acogida, desde la perspectiva de la organización se orienta a:',8,13,1,1,1),
(160,'El proceso de adaptación, inducción y acogida son optimizados utilizando principalmente:',9,13,1,1,1),
(161,'La inducción, acogida y adaptación de los trabajadores incluye la socialización de sus roles para:',10,13,1,1,1),
(162,'Los procesos de inducción y acogida de los trabajadores incluyen conocimientos necesarios para:',11,13,1,1,1),
(163,'Los procesos de inducción y acogida de los trabajadores incluyen la socialización de:',12,13,1,1,1),

(164,'Desde la perspectiva de los trabajadores, los procesos de capacitación, formación y entrenamiento buscan:',7,14,1,1,1),
(165,'Los procesos de capacitación, formación y entrenamiento se diseñaron para que la organización pueda:',8,14,1,1,1),
(166,'La capacitación, formación y entrenamiento de la organización utilizan como apoyo:',9,14,1,1,1),
(167,'Los programas de capacitación, formación y entrenamiento incluyen componentes orientados a:',10,14,1,1,1),
(168,'La capacitación, formación y entrenamiento permiten el desarrollo de capacidades y conocimientos tendientes a:',11,14,1,1,1),
(169,'La capacitación, formación y entrenamiento de los trabajadores generan aprendizaje de:',12,14,1,1,1),

(170,'La evaluación del desempeño en la organización se enfoca en:',7,15,1,1,1),
(171,'La evaluación de desempeño de los trabajadores permite a la organización:',8,15,1,1,1),
(172,'En la evaluación y gestión del desempeño de los trabajadores y de la organización se utilizan:',9,15,1,1,1),
(173,'La evaluación y gestión del desempeño incluye criterios que buscan:',10,15,1,1,1),
(174,'La evaluación del desempeño incluye elementos que permiten evidenciar si los trabajadores y la organización logran:',11,15,1,1,1),
(175,'La evaluación y gestión del desempeño permite a las personas y la organización ver la importancia de:',12,15,1,1,1),

(176,'Las condiciones salariales de su organización buscan:',7,16,1,1,1),
(177,'Las condiciones salariales desde el punto de vista de la organización buscan:',8,16,1,1,1),
(178,'La gestión de los salarios y compensaciones para los trabajadores se realiza utilizando:',9,16,1,1,1),
(179,'Las condiciones de la política y estrategias de compensación y salarios incluyen condiciones orientadas a:',10,16,1,1,1),
(180,'Las condiciones salariales de la organización permiten la participación de las personas para:',11,16,1,1,1),
(181,'Las condiciones salariales de la empresa para los trabajadores:',12,16,1,1,1),

(182,'Los beneficios no salariales definidos en su organización buscan:',7,17,1,1,1),
(183,'Los beneficios no salariales fueron definidos por la organización con el propósito de:',8,17,1,1,1),
(184,'Los beneficios no constitutivos de salario se gestionan utilizando principalmente:',9,17,1,1,1),
(185,'Los beneficios no salariales que se ofrecen a las personas incentivan su orientación hacia:',10,17,1,1,1),
(186,'Los beneficios no salariales diseñados para los trabajadores permiten:',11,17,1,1,1),
(187,'Los beneficios no salariales definidos para los trabajadores, promueven su interés por:',12,17,1,1,1),

(188,'Los programas de crecimiento y desarrollo de los trabajadores está orientado a:',7,18,1,1,1),
(189,'Los programas de crecimiento y desarrollo para los trabajadores permiten a la organización:',8,18,1,1,1),
(190,'Los programas de desarrollo y crecimiento de los trabajadores se apoyan tecnológicamente en:',9,18,1,1,1),
(191,'El plan de desarrollo y crecimiento de las personas tiene en cuenta el aporte de los trabajadores para:',10,18,1,1,1),
(192,'Los programas de crecimiento y desarrollo de los trabajadores han considerado la forma en que es posible: ',11,18,1,1,1),
(193,'El crecimiento y desarrollo de los trabajadores se realiza mediante programas orientados a:',12,18,1,1,1),

(194,'El sistema de gestión de seguridad y salud en el trabajo se ha implementado para:',7,19,1,1,1),
(195,'El sistema de gestión de seguridad y salud en el trabajo se está implementando de manera que la organización pueda:',8,19,1,1,1),
(196,'El cumplimiento de los requisitos del SG-SST de la organización se registran mediante el uso de:',9,19,1,1,1),
(197,'El SG-SST ha identificado las responsabilidades que se tienen para:',10,19,1,1,1),
(198,'El SG-SST permite proteger los propósitos organizacionales a partir de:',11,19,1,1,1),
(199,'El SG-SST implementado en la empresa considera los impactos generados sobre:',12,19,1,1,1),

(200,'El propósito del programa de flexibilidad laboral es:',7,20,1,1,1),
(201,'El programa de flexibilidad laboral de la organización permite:',8,20,1,1,1),
(202,'Las condiciones de flexibilidad en el trabajo se gestionan y optimizan utilizando:',9,20,1,1,1),
(203,'Las condiciones de flexibilidad de tiempo y trabajo tienen incluidas características orientadas en:',10,20,1,1,1),
(204,'Las condiciones de flexibilidad laboral tienen en cuenta las condiciones particulares de los trabajadores, permitiendo:',11,20,1,1,1),
(205,'Las condiciones de flexibilidad laboral de la organización se articula con las condiciones dadas por:',12,20,1,1,1),

(206,'El trabajo colaborativo que es promovido por la organización busca:',7,21,1,1,1),
(207,'El trabajo colaborativo para la organización hace posible:',8,21,1,1,1),
(208,'La gestión de los equipos de trabajo se realiza mediante el uso de:',9,21,1,1,1),
(209,'Además del cumplimiento de los objetivos de cada cargo o proyecto, el trabajo colaborativo debe orientarse a:',10,21,1,1,1),
(210,'El esfuerzo y recursos invertidos por los individuos y grupos de trabajo permiten a la organización:',11,21,1,1,1),
(211,'El trabajo en equipos permite cumplir propósitos relacionados con:',12,21,1,1,1),

(212,'Cuando los trabajadores participan en la toma de decisiones, los resultados se ven reflejados en:',7,22,1,1,1),
(213,'Desde la perspectiva de la organización, la participación de los trabajadores en la toma de decisiones permite:',8,22,1,1,1),
(214,'La toma de decisiones con la participación de los trabajadores se realiza y sistematiza utilizando:',9,22,1,1,1),
(215,'La generación de valor a las partes interesadas a partir de la participación de los trabajadores, incluye indicadores enfocados en:',10,22,1,1,1),
(216,'Los trabajadores y equipos de trabajo generan valor a la organización y sus partes interesadas por medio de: ',11,22,1,1,1),
(217,'El valor generado a la organización y a las partes interesadas a partir de la participación de los trabajadores considera:',12,22,1,1,1),

(218,'El intercambio de información entre los trabajadores y equipos de trabajo permite:',7,23,1,1,1),
(219,'El intercambio de información entre los niveles y equipos de trabajo, le permite a la organización:',8,23,1,1,1),
(220,'El intercambio de información entre los niveles y equipos de trabajo se realiza principalmente usando:',9,23,1,1,1),
(221,'El intercambio de información entre los niveles y equipos de trabajo, genera la posibilidad de:',10,23,1,1,1),
(222,'En la organización, el intercambio de información entre niveles y equipos de trabajo se realiza para lograr:',11,23,1,1,1),
(223,'El intercambio de información en la organización permite que los trabajadores, equipos y la organización actúen según:',12,23,1,1,1),

(224,'Las estrategias de gestión del conocimiento se han definido en la organización para permitir:',7,24,1,1,1),
(225,'Las mejoras de la organización basadas en la gestión del conocimiento le dan a la empresa la facilidad de:',8,24,1,1,1),
(226,'La gestión del conocimiento de la organización se ejecuta por medio de: ',9,24,1,1,1),
(227,'La gestión del conocimiento organizacional incluye dentro de sus beneficios: ',10,24,1,1,1),
(228,'La gestión del conocimiento de la empresa permite:',11,24,1,1,1),
(229,'La gestión del conocimiento organizacional permite considerar un entorno que incluye:',12,24,1,1,1);
