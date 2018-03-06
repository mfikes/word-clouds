(ns word-clouds.core
  (:require
   [clojure.string :as string]
   #?(:cljs [planck.core :refer [line-seq]])
   #?(:clj  [clojure.java.io :as io]
      :cljs [planck.io :as io])))

(def common-words (->> "common_words.txt"
                    io/resource
                    io/reader
                    line-seq
                    (into #{})))

(defn -main [input-file]
  (let [lines     (-> input-file
                    io/file
                    io/reader
                    line-seq)
        words     (->> lines
                    (mapcat #(string/split % #"[\s.,]+"))
                    (map string/lower-case))
        top-words (->> words
                    (remove common-words)
                    frequencies
                    (sort-by val >)
                    (take 25))]
    (run! #(apply println (reverse %)) top-words)))
