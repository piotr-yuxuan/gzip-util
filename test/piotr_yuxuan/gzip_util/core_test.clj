(ns piotr-yuxuan.gzip-util.core-test
  (:use clojure.test
        piotr-yuxuan.gzip-util.core)
  (:import (java.io ByteArrayInputStream)))

(deftest str-round-trip
  (testing "Round trip a UTF-8 encoded string through
  str->gzipped-bytes and gzipped-input-stream->str."
    (let [test-str "√2"]
      (is (= test-str
             (gzipped-input-stream->str
               (ByteArrayInputStream.
                 (str->gzipped-bytes test-str))
               "UTF-8")
             (gzipped-input-stream->str
               (ByteArrayInputStream.
                 (str->gzipped-bytes test-str)))
             (gzipped-bytes->str
               (str->gzipped-bytes test-str)
               "UTF-8")
             (gzipped-bytes->str
               (str->gzipped-bytes test-str)))))))
