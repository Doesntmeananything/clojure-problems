(ns clojure-problems.core)

"Write a function which returns the second to last element from a sequence."
(= (fn [x] (nth x (- (count x) 2)) (list 1 2 3 4 5)) 4)

"Write a function which returns the Nth element from a sequence."
(= (#(last (take (inc %2) %1))'(4 5 6 7) 2) 6)

"Write a function which returns the total number of elements in a sequence."
(= ( #(reduce
       (fn [c _] (inc c))
       0 %)'(1 2 3 3 1)) 5)

"Write a function which reverses a sequence."
(= ((fn [seq] (into '() seq)) [1 2 3 4 5]) [5 4 3 2 1])

"Write a function which returns the sum of a sequence of numbers."
(= ((fn [seq] (reduce + seq)) [1 2 3]) 6)

"Write a function which returns only the odd numbers from a sequence."
(= ((fn [seq] (filter odd? seq)) #{1 2 3 4 5}) '(1 3 5))

"Write a function which returns the first X fibonacci numbers."
(= ((fn [n] (take n
      ((fn fib-recur [a b]
         (cons a (lazy-seq (fib-recur b (+ a b))))) 1 1)) 8)
    '(1 1 2 3 5 8 13 21)))

"Write a function which returns true if the given sequence is a palindrome."
(true? ((fn [seq]
          (if (string? seq)
            (= seq (apply str (reverse seq)))
            (= seq (reverse seq)))) '(1 1 3 3 1 1)))

"Write a function which flattens a sequence."
(= ((fn flat [seq]
     (let [[x & xs] seq]
       (cond
         (empty? seq) '()
         (coll? x) (concat (flat x) (flat xs))
         :else (cons x (flat xs)))))'((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))
