SET PAGESIZE 0;
SPOOL bookCopies.js;

SELECT 'db.Books.drop();' FROM dual;
SELECT 'db.Book_Copies.drop();' FROM dual;

SELECT
  'db.Books.insertOne({ ISBN: "' || ISBN || '", Title: "' || Title || '", Edition: "' || Edition || '", Category: "' || Category || '", Price: ' || Price || ', Book_Copies: [] });'
FROM
  Books;

SELECT
  'db.Book_Copies.insertOne({ ISBN: "' || ISBN || '", CopyNumber: ' || Copy# || ', Status: "' || Status || '" });'
FROM
  Book_Copies;

SPOOL OFF;
