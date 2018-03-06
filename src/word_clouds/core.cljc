(ns word-clouds.core
  (:require
   [clojure.string :as string]
   #?(:cljs [planck.core :refer [line-seq with-open]])
   #?(:clj  [clojure.java.io :as io]
      :cljs [planck.io :as io])))

(def common-words (with-open [rdr (io/reader (io/resource "common_words.txt"))]
                    (into #{} (line-seq rdr))))

(defn top-words [rdr]
  (->> rdr
    line-seq
    (mapcat #(string/split % #"[\s.,]+"))
    (map string/lower-case)
    (remove common-words)
    frequencies
    (sort-by val >)
    (take 25)))

(defn -main [input-file]
  (with-open [rdr (io/reader input-file)]
    (run! #(apply println (reverse %)) (top-words rdr))))
