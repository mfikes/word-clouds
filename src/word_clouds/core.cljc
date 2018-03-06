(ns word-clouds.core
  (:require
   [clojure.string :as string]
   #?(:cljs [planck.core :refer [line-seq slurp with-open]])
   #?(:clj  [clojure.java.io :as io]
      :cljs [planck.io :as io])))

(def common-words (with-open [rdr (io/reader (io/resource "common_words.txt"))]
                    (into #{} (line-seq rdr))))

(defn top-words [n text]
  (->> text
    string/split-lines
    (mapcat #(string/split % #"[\s.,]+"))
    (map string/lower-case)
    (remove common-words)
    frequencies
    (sort-by val >)
    (take n)))

(defn -main [input-file]
  (->> (slurp input-file)
    (top-words 25)
    (run! #(apply println (reverse %)))))
