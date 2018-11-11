# Лабораторная работа №2.
## Вариант 4. Логические формулы в стиле Си
Логические формулы. Используются операции &, |, ^, !. Приоритет
операций стандартный. Скобки могут использоваться для изменения
приоритета.
В качестве операндов выступают переменные с именем из одной буквы.
Используйте один терминал для всех переменных. Для каждой логической
операции должен быть заведен один терминал.
Пример: (!a | b) & a & (a | !(b ^ c))

# Построим грамматику

* `FORMULA -> XOR "|" FORMULA | XOR`
* `XOR -> AND "^" XOR | AND`
* `AND -> NEGATION "&" SUBFORMULA | NEGATION`
* `NEGATION -> "!" SUBFORMULA | SUBFORMULA`
* `SUBFORMULA -> [a-zA-Z] | "(" FORMULA ")"`

## Описание 

Нетерминал    | Значение
------------- | -------------
FORMULA  | Логическая формула или XOR
XOR | Исключающее ИЛИ или AND
AND | Логическое И или NEGATION
NEGATION | Отрицание или SUBFORMULA
SUBFORMULA | Имя переменной или формула в скобках
