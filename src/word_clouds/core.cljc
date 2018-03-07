(ns word-clouds.core
  (:require
   [clojure.string :as string]
   #?(:cljs [planck.core :refer [line-seq slurp with-open]])
   #?(:clj  [clojure.java.io :as io]
      :cljs [planck.io :as io])))

(def common-words (with-open [rdr (io/reader (io/resource "common_words.txt"))]
                    (into #{} (line-seq rdr))))

(defn -main [input-file]
  (->> (string/split (slurp input-file) #"[\s.,]+")
    (map string/lower-case)
    (remove common-words)
    frequencies
    (sort-by val >)
    (take 25)
    (map reverse)
    (run! #(apply println %))))
