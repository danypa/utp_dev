INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad) VALUES (1,'En una sesión de aprendizaje, ¿qué acción favorece mejor la evaluación formativa?','Brindar retroalimentación oportuna para que el estudiante mejore durante el proceso.','INTERMEDIA');
SET @p = LAST_INSERT_ID();
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Aplicar solo una prueba final',FALSE,1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Retroalimentar durante el proceso',TRUE,2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Registrar únicamente asistencia',FALSE,3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Evitar criterios de evaluación',FALSE,4);
INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad) VALUES (1,'¿Cuál es una característica de una competencia?','Integra conocimientos, habilidades y actitudes para actuar pertinentemente.','INTERMEDIA');
SET @p = LAST_INSERT_ID();
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Se limita a memorizar datos',FALSE,1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Integra saberes para actuar',TRUE,2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Solo mide velocidad',FALSE,3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'No requiere contexto',FALSE,4);
INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad) VALUES (2,'¿Qué elemento permite verificar el avance hacia un propósito de aprendizaje?','Los criterios y evidencias de aprendizaje.','INTERMEDIA');
SET @p = LAST_INSERT_ID();
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'El color del material',FALSE,1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'La lista de asistencia',FALSE,2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Los criterios y evidencias',TRUE,3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'El orden alfabético',FALSE,4);
INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad) VALUES (2,'Ante diferentes ritmos de aprendizaje, una práctica pertinente es:','Aplicar estrategias diferenciadas según las necesidades identificadas.','INTERMEDIA');
SET @p = LAST_INSERT_ID();
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Usar una única estrategia siempre',FALSE,1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Diferenciar apoyos y estrategias',TRUE,2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Eliminar la retroalimentación',FALSE,3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Evaluar solo al final',FALSE,4);
INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad) VALUES (3,'Una actividad que promueve pensamiento crítico debe priorizar:','La argumentación sustentada y el análisis de evidencias.','INTERMEDIA');
SET @p = LAST_INSERT_ID();
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'La repetición literal',FALSE,1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'La copia de definiciones',FALSE,2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'El análisis de evidencias',TRUE,3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'La memorización mecánica',FALSE,4);
INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad) VALUES (3,'Para atender la diversidad del aula, el docente debe:','Planificar apoyos y oportunidades de participación para todos.','INTERMEDIA');
SET @p = LAST_INSERT_ID();
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Aplicar idénticos apoyos sin diagnóstico',FALSE,1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Planificar apoyos pertinentes',TRUE,2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Excluir actividades complejas',FALSE,3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Reducir los objetivos sin evaluar',FALSE,4);
INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad) VALUES (4,'La convivencia escolar se fortalece principalmente cuando:','Existen acuerdos claros, participación y resolución dialogada de conflictos.','INTERMEDIA');
SET @p = LAST_INSERT_ID();
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Se evitan todos los desacuerdos',FALSE,1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Solo decide la autoridad',FALSE,2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Hay acuerdos y diálogo',TRUE,3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'No se revisan normas',FALSE,4);
INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad) VALUES (4,'Una decisión de gestión basada en evidencia requiere:','Analizar información relevante antes de definir acciones de mejora.','INTERMEDIA');
SET @p = LAST_INSERT_ID();
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Actuar sin datos',FALSE,1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Analizar información relevante',TRUE,2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Usar rumores como fuente',FALSE,3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) VALUES (@p,'Evitar indicadores',FALSE,4);

USE magistra;

-- Agrega tres preguntas a cada uno de los cuatro temas de práctica.
-- Se puede ejecutar más de una vez: no duplica preguntas ni alternativas.

-- Tema: Conocimientos Curriculares y Pedagógicos
INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad)
SELECT t.id_tema,'Al planificar una experiencia de aprendizaje, ¿qué relación debe existir entre propósito, criterios y evidencias?','Deben estar alineados para que las evidencias permitan valorar el logro del propósito según los criterios.','INTERMEDIA'
FROM temas t WHERE t.nombre='Conocimientos Curriculares y Pedagógicos'
AND NOT EXISTS (SELECT 1 FROM preguntas p WHERE p.id_tema=t.id_tema AND p.enunciado='Al planificar una experiencia de aprendizaje, ¿qué relación debe existir entre propósito, criterios y evidencias?');
SET @p = (SELECT p.id_pregunta FROM preguntas p JOIN temas t ON t.id_tema=p.id_tema WHERE t.nombre='Conocimientos Curriculares y Pedagógicos' AND p.enunciado='Al planificar una experiencia de aprendizaje, ¿qué relación debe existir entre propósito, criterios y evidencias?');
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Deben alinearse para valorar el propósito',TRUE,1 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Las evidencias se eligen sin considerar los criterios',FALSE,2 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'El propósito se define después de calificar',FALSE,3 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Los criterios solo sirven para registrar asistencia',FALSE,4 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=4);

INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad)
SELECT t.id_tema,'¿Qué característica debe tener una retroalimentación formativa para ayudar al estudiante?','Debe ser específica, oportuna y orientar acciones concretas para mejorar el aprendizaje.','INTERMEDIA'
FROM temas t WHERE t.nombre='Conocimientos Curriculares y Pedagógicos'
AND NOT EXISTS (SELECT 1 FROM preguntas p WHERE p.id_tema=t.id_tema AND p.enunciado='¿Qué característica debe tener una retroalimentación formativa para ayudar al estudiante?');
SET @p = (SELECT p.id_pregunta FROM preguntas p JOIN temas t ON t.id_tema=p.id_tema WHERE t.nombre='Conocimientos Curriculares y Pedagógicos' AND p.enunciado='¿Qué característica debe tener una retroalimentación formativa para ayudar al estudiante?');
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Ser específica y señalar cómo mejorar',TRUE,1 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Limitarse a indicar una nota',FALSE,2 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Comparar al estudiante con sus compañeros',FALSE,3 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Entregarse únicamente al final del año',FALSE,4 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=4);

INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad)
SELECT t.id_tema,'¿Qué propósito tiene plantear una situación significativa en una actividad de aprendizaje?','Permite movilizar competencias para responder a un reto relacionado con un contexto y propósito.','INTERMEDIA'
FROM temas t WHERE t.nombre='Conocimientos Curriculares y Pedagógicos'
AND NOT EXISTS (SELECT 1 FROM preguntas p WHERE p.id_tema=t.id_tema AND p.enunciado='¿Qué propósito tiene plantear una situación significativa en una actividad de aprendizaje?');
SET @p = (SELECT p.id_pregunta FROM preguntas p JOIN temas t ON t.id_tema=p.id_tema WHERE t.nombre='Conocimientos Curriculares y Pedagógicos' AND p.enunciado='¿Qué propósito tiene plantear una situación significativa en una actividad de aprendizaje?');
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Plantear un reto contextualizado que movilice competencias',TRUE,1 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Aumentar la cantidad de tareas sin propósito',FALSE,2 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Reemplazar los propósitos de aprendizaje',FALSE,3 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Evitar que los estudiantes expliquen sus decisiones',FALSE,4 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=4);

-- Tema: Gestión del aprendizaje
INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad)
SELECT t.id_tema,'Antes de iniciar una unidad, ¿para qué puede servir recoger información sobre los saberes previos?','Ayuda a reconocer los puntos de partida y ajustar la planificación a las necesidades del grupo.','INTERMEDIA'
FROM temas t WHERE t.nombre='Gestión del aprendizaje'
AND NOT EXISTS (SELECT 1 FROM preguntas p WHERE p.id_tema=t.id_tema AND p.enunciado='Antes de iniciar una unidad, ¿para qué puede servir recoger información sobre los saberes previos?');
SET @p = (SELECT p.id_pregunta FROM preguntas p JOIN temas t ON t.id_tema=p.id_tema WHERE t.nombre='Gestión del aprendizaje' AND p.enunciado='Antes de iniciar una unidad, ¿para qué puede servir recoger información sobre los saberes previos?');
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Para ajustar la planificación al punto de partida del grupo',TRUE,1 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Para asignar calificaciones finales de inmediato',FALSE,2 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Para excluir a quienes tienen dificultades',FALSE,3 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Para evitar modificar la planificación',FALSE,4 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=4);

INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad)
SELECT t.id_tema,'Durante una actividad, varios estudiantes muestran una dificultad que no se había previsto. ¿Qué debería hacer el docente?','Debe analizar la evidencia y ajustar la estrategia o los apoyos para facilitar el aprendizaje.','INTERMEDIA'
FROM temas t WHERE t.nombre='Gestión del aprendizaje'
AND NOT EXISTS (SELECT 1 FROM preguntas p WHERE p.id_tema=t.id_tema AND p.enunciado='Durante una actividad, varios estudiantes muestran una dificultad que no se había previsto. ¿Qué debería hacer el docente?');
SET @p = (SELECT p.id_pregunta FROM preguntas p JOIN temas t ON t.id_tema=p.id_tema WHERE t.nombre='Gestión del aprendizaje' AND p.enunciado='Durante una actividad, varios estudiantes muestran una dificultad que no se había previsto. ¿Qué debería hacer el docente?');
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Revisar la evidencia y ajustar estrategias o apoyos',TRUE,1 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Continuar sin atender la dificultad observada',FALSE,2 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Cambiar el propósito para evitar evaluarlo',FALSE,3 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Responsabilizar únicamente a los estudiantes',FALSE,4 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=4);

INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad)
SELECT t.id_tema,'¿Qué acción favorece la participación de estudiantes con distintos ritmos de aprendizaje?','Ofrecer apoyos y formas variadas de participación manteniendo el propósito de aprendizaje.','INTERMEDIA'
FROM temas t WHERE t.nombre='Gestión del aprendizaje'
AND NOT EXISTS (SELECT 1 FROM preguntas p WHERE p.id_tema=t.id_tema AND p.enunciado='¿Qué acción favorece la participación de estudiantes con distintos ritmos de aprendizaje?');
SET @p = (SELECT p.id_pregunta FROM preguntas p JOIN temas t ON t.id_tema=p.id_tema WHERE t.nombre='Gestión del aprendizaje' AND p.enunciado='¿Qué acción favorece la participación de estudiantes con distintos ritmos de aprendizaje?');
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Proporcionar apoyos y diversas formas de participar',TRUE,1 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Usar una sola forma de trabajo para todos',FALSE,2 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Reducir las oportunidades de colaboración',FALSE,3 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Separar permanentemente a quienes necesitan apoyo',FALSE,4 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=4);

-- Tema: Competencias pedagógicas
INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad)
SELECT t.id_tema,'En una evaluación por competencias, ¿qué tipo de tarea permite recoger mejor evidencias de desempeño?','Una tarea contextualizada que requiere movilizar conocimientos y habilidades para resolver un reto.','INTERMEDIA'
FROM temas t WHERE t.nombre='Competencias pedagógicas'
AND NOT EXISTS (SELECT 1 FROM preguntas p WHERE p.id_tema=t.id_tema AND p.enunciado='En una evaluación por competencias, ¿qué tipo de tarea permite recoger mejor evidencias de desempeño?');
SET @p = (SELECT p.id_pregunta FROM preguntas p JOIN temas t ON t.id_tema=p.id_tema WHERE t.nombre='Competencias pedagógicas' AND p.enunciado='En una evaluación por competencias, ¿qué tipo de tarea permite recoger mejor evidencias de desempeño?');
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Una tarea contextualizada que plantea un reto',TRUE,1 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Una lista de términos para copiar',FALSE,2 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Una actividad sin relación con el propósito',FALSE,3 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Una prueba centrada solo en recordar datos aislados',FALSE,4 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=4);

INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad)
SELECT t.id_tema,'¿Qué práctica ayuda a que los estudiantes construyan explicaciones con pensamiento crítico?','Promover que analicen información, contrasten evidencias y sustenten sus conclusiones.','INTERMEDIA'
FROM temas t WHERE t.nombre='Competencias pedagógicas'
AND NOT EXISTS (SELECT 1 FROM preguntas p WHERE p.id_tema=t.id_tema AND p.enunciado='¿Qué práctica ayuda a que los estudiantes construyan explicaciones con pensamiento crítico?');
SET @p = (SELECT p.id_pregunta FROM preguntas p JOIN temas t ON t.id_tema=p.id_tema WHERE t.nombre='Competencias pedagógicas' AND p.enunciado='¿Qué práctica ayuda a que los estudiantes construyan explicaciones con pensamiento crítico?');
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Analizar y contrastar evidencias antes de concluir',TRUE,1 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Aceptar la primera opinión sin examinarla',FALSE,2 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Memorizar una conclusión única',FALSE,3 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Evitar que los estudiantes expliquen sus razones',FALSE,4 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=4);

INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad)
SELECT t.id_tema,'Al atender la diversidad del aula, ¿cómo se mantiene una enseñanza inclusiva?','Se identifican barreras y se ofrecen apoyos pertinentes para que todos puedan participar y aprender.','INTERMEDIA'
FROM temas t WHERE t.nombre='Competencias pedagógicas'
AND NOT EXISTS (SELECT 1 FROM preguntas p WHERE p.id_tema=t.id_tema AND p.enunciado='Al atender la diversidad del aula, ¿cómo se mantiene una enseñanza inclusiva?');
SET @p = (SELECT p.id_pregunta FROM preguntas p JOIN temas t ON t.id_tema=p.id_tema WHERE t.nombre='Competencias pedagógicas' AND p.enunciado='Al atender la diversidad del aula, ¿cómo se mantiene una enseñanza inclusiva?');
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Identificando barreras y brindando apoyos pertinentes',TRUE,1 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Aplicando las mismas ayudas sin observar necesidades',FALSE,2 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Reduciendo la participación de algunos estudiantes',FALSE,3 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Evitando recoger evidencias de aprendizaje',FALSE,4 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=4);

-- Tema: Normativa y gestión educativa
INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad)
SELECT t.id_tema,'¿Qué práctica fortalece una gestión escolar orientada a la mejora continua?','Definir acciones a partir del análisis de evidencias y revisar periódicamente sus resultados.','INTERMEDIA'
FROM temas t WHERE t.nombre='Normativa y gestión educativa'
AND NOT EXISTS (SELECT 1 FROM preguntas p WHERE p.id_tema=t.id_tema AND p.enunciado='¿Qué práctica fortalece una gestión escolar orientada a la mejora continua?');
SET @p = (SELECT p.id_pregunta FROM preguntas p JOIN temas t ON t.id_tema=p.id_tema WHERE t.nombre='Normativa y gestión educativa' AND p.enunciado='¿Qué práctica fortalece una gestión escolar orientada a la mejora continua?');
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Analizar evidencias y revisar los resultados de las acciones',TRUE,1 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Mantener acciones sin evaluar sus efectos',FALSE,2 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Tomar decisiones basándose solo en suposiciones',FALSE,3 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Evitar comunicar los objetivos de mejora',FALSE,4 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=4);

INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad)
SELECT t.id_tema,'Ante un incidente que afecta la convivencia escolar, ¿qué actuación inicial es más adecuada?','Se debe proteger a las personas involucradas, comunicar por los canales correspondientes y seguir los procedimientos aplicables.','INTERMEDIA'
FROM temas t WHERE t.nombre='Normativa y gestión educativa'
AND NOT EXISTS (SELECT 1 FROM preguntas p WHERE p.id_tema=t.id_tema AND p.enunciado='Ante un incidente que afecta la convivencia escolar, ¿qué actuación inicial es más adecuada?');
SET @p = (SELECT p.id_pregunta FROM preguntas p JOIN temas t ON t.id_tema=p.id_tema WHERE t.nombre='Normativa y gestión educativa' AND p.enunciado='Ante un incidente que afecta la convivencia escolar, ¿qué actuación inicial es más adecuada?');
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Proteger a las personas y seguir los procedimientos correspondientes',TRUE,1 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Difundir públicamente los nombres de los involucrados',FALSE,2 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Ignorar el hecho hasta que se repita',FALSE,3 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Resolverlo sin registrar ni comunicar lo ocurrido',FALSE,4 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=4);

INSERT INTO preguntas(id_tema,enunciado,explicacion,dificultad)
SELECT t.id_tema,'¿Cómo puede participar la comunidad educativa en la planificación de mejoras de la institución?','Puede aportar necesidades y propuestas, y colaborar en el seguimiento de objetivos acordados.','INTERMEDIA'
FROM temas t WHERE t.nombre='Normativa y gestión educativa'
AND NOT EXISTS (SELECT 1 FROM preguntas p WHERE p.id_tema=t.id_tema AND p.enunciado='¿Cómo puede participar la comunidad educativa en la planificación de mejoras de la institución?');
SET @p = (SELECT p.id_pregunta FROM preguntas p JOIN temas t ON t.id_tema=p.id_tema WHERE t.nombre='Normativa y gestión educativa' AND p.enunciado='¿Cómo puede participar la comunidad educativa en la planificación de mejoras de la institución?');
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Aportando propuestas y participando en el seguimiento',TRUE,1 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=1);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Recibiendo decisiones sin conocer los objetivos',FALSE,2 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=2);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Limitando la planificación a una sola persona',FALSE,3 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=3);
INSERT INTO alternativas(id_pregunta,texto,correcta,orden) SELECT @p,'Evitando revisar los compromisos asumidos',FALSE,4 WHERE @p IS NOT NULL AND NOT EXISTS (SELECT 1 FROM alternativas WHERE id_pregunta=@p AND orden=4);

-- Verificación: cada tema debe tener cinco preguntas activas y cada nueva pregunta cuatro alternativas, una correcta.
SELECT t.nombre AS tema, COUNT(DISTINCT p.id_pregunta) AS preguntas_activas
FROM temas t LEFT JOIN preguntas p ON p.id_tema=t.id_tema AND p.estado=1
GROUP BY t.id_tema,t.nombre ORDER BY t.id_tema;

SELECT t.nombre AS tema, p.enunciado, COUNT(a.id_alternativa) AS alternativas,
       SUM(a.correcta=1) AS respuestas_correctas
FROM temas t JOIN preguntas p ON p.id_tema=t.id_tema
LEFT JOIN alternativas a ON a.id_pregunta=p.id_pregunta
WHERE p.enunciado IN (
 'Al planificar una experiencia de aprendizaje, ¿qué relación debe existir entre propósito, criterios y evidencias?',
 '¿Qué característica debe tener una retroalimentación formativa para ayudar al estudiante?',
 '¿Qué propósito tiene plantear una situación significativa en una actividad de aprendizaje?',
 'Antes de iniciar una unidad, ¿para qué puede servir recoger información sobre los saberes previos?',
 'Durante una actividad, varios estudiantes muestran una dificultad que no se había previsto. ¿Qué debería hacer el docente?',
 '¿Qué acción favorece la participación de estudiantes con distintos ritmos de aprendizaje?',
 'En una evaluación por competencias, ¿qué tipo de tarea permite recoger mejor evidencias de desempeño?',
 '¿Qué práctica ayuda a que los estudiantes construyan explicaciones con pensamiento crítico?',
 'Al atender la diversidad del aula, ¿cómo se mantiene una enseñanza inclusiva?',
 '¿Qué práctica fortalece una gestión escolar orientada a la mejora continua?',
 'Ante un incidente que afecta la convivencia escolar, ¿qué actuación inicial es más adecuada?',
 '¿Cómo puede participar la comunidad educativa en la planificación de mejoras de la institución?'
)
GROUP BY t.nombre,p.id_pregunta,p.enunciado ORDER BY t.nombre,p.id_pregunta;
