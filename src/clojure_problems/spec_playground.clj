(ns clojure-problems.spec-playground
  (:require [clojure.repl :refer [doc]]
            [clojure.spec.alpha :as s])
  (:import java.util.Date))

(s/conform even? 1000)
;; => 1000

(s/valid? even? 10)
;; => true

(s/valid? nil? nil)
;; => true

(s/valid? string? "abc")
;; => true

(s/valid? #(> % 5) 10)
;; => true

(s/valid? #(> % 5) 0)
;; => false

(s/valid? inst? (Date.))
;; => true

(s/valid? #{:club :diamond :heart :spade} :club)
;; => true
(s/valid? #{:club :diamond :heart :spade} 42)
;; => false

(s/valid? #{42} 42)
;; => true

;; Using registries

(s/def ::date inst?)
(s/def ::suit #{:club :diamond :heart :spade})

(s/valid? ::date (java.util.Date.))
;; => true

(s/conform ::suit :club)
;; => :club

(doc ::date)
(doc ::suit)

;; Composing predicates

(s/def ::big-even (s/and int? even? #(> % 1000)))
(s/valid? ::big-even :foo)
;; => false
(s/valid? ::big-even 10)
;; => false
(s/valid? ::big-even 100000)
;; => true

(s/def ::name-or-id (s/or :name string?
                          :id int?))
(s/valid? ::name-or-id "abs")
;; => true
(s/valid? ::name-or-id 100)
;; => true
(s/valid? ::name-or-id :foo)
;; => false

(s/conform ::name-or-id "abc")
;; => [:name "abc"]
(s/conform ::name-or-id 100)
;; => [:id 100]

(s/valid? string? nil)
;; => false
(s/valid? (s/nilable string?) nil)
;; => true

;; Explain

(s/explain ::suit 42)
(s/explain ::big-even 5)
(s/explain ::name-or-id :foo)

(s/explain-data ::name-or-id :foo)

;; Entity Maps

(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")

(s/def ::email-type (s/and string? #(re-matches email-regex %)))

(s/def ::acctid int?)
(s/def ::first-name string?)
(s/def ::last-name string?)
(s/def ::email ::email-type)

(s/def ::person (s/keys :req [::first-name ::last-name ::email]
                        :opt [::phone]))

(s/valid? ::person
          {::first-name "Bugs"
           ::last-name "Bunny"
           ::email "bugs@example.com"})
;; => true

(s/explain ::person
           {::first-name "Bugs"})
;; => fails required key check

(s/explain ::person
           {::first-name "Bugs"
            ::last-name "Bunny"
            ::email "n/a"})
;; => fails attribute conformance

;; using unqalified keys

(s/def :unq/person
  (s/keys :req-un [::first-name ::last-name ::email]
          :opt-un [::phone]))

(s/conform :unq/person
           {:first-name "Bugs"
            :last-name "Bunny"
            :email "bugs@example.com"})

(s/explain :unq/person
           {:first-name "Bugs"
            :last-name "Bunny"
            :email "n/a"})
;; => fails attribute conformance

(s/explain :unq/person
           {:first-name "Bugs"})
;; => fails required key check

(defrecord Person [first-name last-name email phone])

(s/explain :unq/person
           (->Person "Bugs" nil nil nil))
;; => fails required key check

(s/conform :unq/person
           (->Person "Bugs" "Bunny" "bugs@example.com" nil))
