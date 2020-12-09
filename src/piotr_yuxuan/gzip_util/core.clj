(ns ^{:doc "Some utility functions for compressing/uncompressing data using gzip"
      :author "Greg Phillips, https://github.com/gphil"}
  piotr-yuxuan.gzip-util.core
  (:require [clojure.java.io :as io])
  (:import [java.io ByteArrayOutputStream]
           [java.util.zip GZIPInputStream GZIPOutputStream]))

(defn str->gzipped-bytes
  [str]
  (with-open [out (ByteArrayOutputStream.)
              gzip (GZIPOutputStream. out)]
    (do
      (.write gzip (.getBytes str))
      (.finish gzip)
      (.toByteArray out))))

(defn gzipped-input-stream->str
  [input-stream encoding]
  (with-open [out (ByteArrayOutputStream.)]
    (io/copy (GZIPInputStream. input-stream) out)
    (.close input-stream)
    (.toString out encoding)))
