(ns ^{:doc "Some utility functions for compressing/uncompressing data using gzip"
      :author "Greg Phillips, https://github.com/gphil"}
  piotr-yuxuan.gzip-util.core
  (:require [clojure.java.io :as io])
  (:import [java.io ByteArrayOutputStream InputStream ByteArrayInputStream]
           [java.util.zip GZIPInputStream GZIPOutputStream]))

(defn ^"[B" str->gzipped-bytes
  [^String s]
  (with-open [out (ByteArrayOutputStream.)
              gzip (GZIPOutputStream. out)]
    (do
      (.write gzip (.getBytes s))
      (.finish gzip)
      (.toByteArray out))))

(defn ^String gzipped-input-stream->str
  ([^InputStream input-stream]
   (gzipped-input-stream->str input-stream "UTF-8"))
  ([^InputStream input-stream ^String encoding]
   (with-open [out (ByteArrayOutputStream.)]
     (io/copy (GZIPInputStream. input-stream) out)
     (.close input-stream)
     (.toString out encoding))))

(defn ^String gzipped-bytes->str
  ([^"[B" bs]
   (gzipped-input-stream->str (ByteArrayInputStream. bs)))
  ([^"[B" bs ^String encoding]
   (gzipped-input-stream->str (ByteArrayInputStream. bs) encoding)))
