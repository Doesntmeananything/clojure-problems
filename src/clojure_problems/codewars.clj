(ns clojure-problems.codewars)

(defn digitize [n]
  (->> n
       (iterate #(quot % 10))
       (take-while pos?)
       (mapv #(mod % 10))))

(digitize 12345)
