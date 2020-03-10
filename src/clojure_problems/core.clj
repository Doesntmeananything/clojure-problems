(ns clojure-problems.core)

"Write a function which returns the second to last element from a sequence."
(= (fn [x] (nth x (- (count x) 2)) (list 1 2 3 4 5)) 4)

"Write a function which returns the Nth element from a sequence."
(= (#(last (take (inc %2) %1)) '(4 5 6 7) 2) 6)

"Write a function which returns the total number of elements in a sequence."
(= (#(reduce
      (fn [c _] (inc c))
      0 %) '(1 2 3 3 1)) 5)

"Write a function which reverses a sequence."
(= ((fn [seq] (into '() seq)) [1 2 3 4 5]) [5 4 3 2 1])

"Write a function which returns the sum of a sequence of numbers."
(= ((fn [seq] (reduce + seq)) [1 2 3]) 6)

"Write a function which returns only the odd numbers from a sequence."
(= ((fn [seq] (filter odd? seq)) #{1 2 3 4 5}) '(1 3 5))

"Write a function which returns the first X fibonacci numbers."
(= ((fn [n] (take n ((fn fib-recur [a b] (cons a (lazy-seq (fib-recur b (+ a b))))) 1 1))) 8)
   '(1 1 2 3 5 8 13 21))

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
          (coll? x)    (concat (flat x) (flat xs))
          :else        (cons x (flat xs))))) '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))

"Write a function which takes a string and returns a new string containing only the capital letters."
(= ((fn [seq]
      (apply str
             (map char
                  (filter #(and (<= 65 %) (<= % 90))
                          (map int seq))))) "HeLlO, WoRlD!") "HLOWRD")

"Write a function which removes consecutive duplicates from a sequence."
(= (apply str ((fn [seq]
                 (map first
                      (partition-by identity seq))) "Leeeeeerrroyyy")) "Leroy")

"Write a function which packs consecutive duplicates into sub-lists."
(= ((fn [seq] (partition-by identity seq)) [1 1 2 1 1 1 3 3]) '((1 1) (2) (1 1 1) (3 3)))

"Write a function which duplicates each element of a sequence."
(= ((fn f [s]
      (let [f*
            (fn [acc s*]
              (conj acc s* s*))]
        (reduce f* [] s))) [1 2 3]) '(1 1 2 2 3 3))

"Write a function which replicates each element of a sequence a variable number of times."
(= ((fn f [s n]
      (let [f*
            (fn [acc s*]
              (concat acc (repeat n s*)))]
        (reduce f* [] s))) [1 2 3] 2) '(1 1 2 2 3 3))

"Write a function which creates a list of all integers in a given range."
(= ((fn [s e]
      (loop [x s acc []]
        (if (< x e)
          (recur (inc x) (conj acc x))
          acc))) 1 4) '(1 2 3))

"Write a function which creates a list of all integers in a given range."
(= ((fn f [s e]
      (loop [x s acc []]
        (if (< x e)
          (recur (inc x) (conj acc x))
          acc))) 1 4) '(1 2 3))

"Write a function which takes a variable number of parameters and returns the maximum value."
(= ((fn [& s]
      (reduce (fn [acc s*]
                (if (> s* acc)
                  s*
                  acc))
              (first s)
              (rest s))) 1 8 3 4) 8)

"Write a function which takes two sequences and returns the first item from each, then the second item from each, then the third, etc."
(= (#(mapcat list %1 %2) [1 2 3] [:a :b :c]) '(1 :a 2 :b 3 :c))

"Write a function which separates the items of a sequence by an arbitrary value."
(= ((fn [x s]
      (rest (mapcat #(list x %1) s))) 0 [1 2 3]) [1 0 2 0 3])

"Write a function which drops every Nth item from a sequence."
(= ((fn [s n]
      (loop [agg [] s* s]
        (if (empty? s*)
          agg
          (recur (concat agg (let [x (take n s*)]
                               (if (> n (count x))
                                 x
                                 (drop-last x))))
                 (drop n s*))))) [1 2 3 4 5 6 7 8] 3) [1 2 4 5 7 8])

"Write a function which calculates factorials."
(= ((fn [n]
      (loop [cnt n acc 1]
        (if (zero? cnt)
          acc
          (recur (dec cnt) (* acc cnt))))) 5) 120)

"Write a function which reverses the interleave process into x number of subsequences."
(= ((fn [s n]
      (let [s* (partition n n s)]
        (for [i (range n)]
          (map #(nth %1 i) s*)))) [1 2 3 4 5 6] 2) '((1 3 5) (2 4 6)))

"Write a function which can rotate a sequence in either direction."
(= ((fn [n s]
      (let [n* (rem n (count s))]
        (if (pos? n*)
          (concat (drop n* s) (take n* s))
          (concat (take-last (unchecked-negate n*) s) (drop-last (unchecked-negate n*) s))))) 2 [1 2 3 4 5]) '(3 4 5 1 2))

"Write a higher-order function which flips the order of the arguments of an input function."
(= 3 (((fn [f]
         (fn [& args]
           (apply f (reverse args)))) nth) 2 [1 2 3 4 5]))

"Write a function which will split a sequence into two parts."
(= ((fn [n s]
      (list (take n s) (drop n s))) 3 [1 2 3 4 5 6]) [[1 2 3] [4 5 6]])
