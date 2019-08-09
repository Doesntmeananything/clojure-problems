(ns clojure-problems.core)

"Write a function which returns the second to last element from a sequence."
(= (fn [x] (nth x (- (count x) 2)) (list 1 2 3 4 5)) 4)

"Write a function which returns the Nth element from a sequence."
(= (#(last (take (inc %2) %1))'(4 5 6 7) 2) 6)

"Write a function which returns the total number of elements in a sequence."
(= (__ '(1 2 3 3 1)) 5)
