INSERT INTO categories_tasks (task_id, category_id)
SELECT t.id, c.id
FROM tasks t, categories c
where c.id = (select id from categories c where c.name = 'Home')