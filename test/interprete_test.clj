(ns interprete-test
  (:require
    [clojure.test :refer :all]
    [definiciones :refer :all]
    [procesamiento :refer :all] :reload-all))

(deftest validez-expresion-suma-dos-numeros
  (testing "La suma de dos numeros debe ser valida"
    (let [
        validez-expresion (expresionValida? '(+ 1 1) {} {})]
    (is (true? validez-expresion)))))

(deftest ejecutar-expresion-suma-dos-numeros
  (testing "La suma es  1 + 1 = 2"
    (let [
        resultado (ejecutarFuncion '(+ 1 1) {} {})]
    (is (= 2 resultado)))))
        
(deftest validez-expresion-true
  (testing "La expresion true debe ser valida"
    (let [
        validez-expresion (expresionValida? 'true {} {})]
    (is (true? validez-expresion)))))

(deftest ejecutar-expresion-true
  (testing "La ejecucion de true es true"
    (let [
        resultado (ejecutarFuncion 'true {} {})]
    (is (true? resultado)))))
    
(deftest validez-expresion-false
  (testing "La expresion false debe ser valida"
    (let [
        validez-expresion (expresionValida? 'false {} {})]
    (is (true? validez-expresion)))))

(deftest ejecutar-expresion-false
  (testing "La ejecucion de false es false"
    (let [
        resultado (ejecutarFuncion 'false {} {})]
    (is (false? resultado)))))
    
(deftest validez-expresion-igual
  (testing "La expresion igual debe ser valida"
    (let [
        validez-expresion (expresionValida? '(= true false) {} {})]
    (is (true? validez-expresion)))))

(deftest ejecutar-expresion-igual-bool-distintos
  (testing "La ejecucion de (= true false) es false"
    (let [
        resultado (ejecutarFuncion '(= true false) {} {})]
    (is (false? resultado)))))
    
(deftest validez-expresion-distinto
  (testing "La expresion distinto debe ser valida"
    (let [
        validez-expresion (expresionValida? '(!= true true) {} {})]
    (is (true? validez-expresion)))))

(deftest ejecutar-expresion-distinto-bool-iguales
  (testing "La ejecucion de (!= true true) es false"
    (let [
        resultado (ejecutarFuncion '(!= true true) {} {})]
    (is (false? resultado)))))
    
(deftest validez-expresion-igual-anidada
  (testing "La expresion igual anidada debe ser valida"
    (let [
        validez-expresion (expresionValida? '(= (= true (= true false)) ( = true (= true (= true true)))) {} {})]
    (is (true? validez-expresion)))))

(deftest validez-expresion-bool-no-implementa-resta
  (testing "La expresion compuesta debe ser no valida. Booleanos no implementa resta"
    (let [
        validez-expresion (expresionValida? '(+ (- 1 true) 1) {} {})]
    (is (false? validez-expresion)))))

(deftest validez-expresion-current
  (testing "La expresion current debe ser valida"
    (let [
        validez-expresion (expresionValida? '(current "spam") {"spam" true, "important" false} {})]
    (is (true? validez-expresion)))))
  
(deftest ejecucion-expresion-current-resultado-true
  (testing "La ejecucion de current debe ser true"
    (let [
        resultado (ejecutarFuncion '(current "spam") {"spam" true, "important" false} {})]
    (is (true? resultado)))))
    
(deftest ejecucion-expresion-current-resultado-false
  (testing "La ejecucion de current debe ser false"
    (let [
        resultado (ejecutarFuncion '(current "spam") {"spam" false, "important" false} {})]
    (is (false? resultado)))))

(deftest validez-tipo-basico-numero
  (testing "Un numero debe ser valido"
    (let [
        validez-expresion (expresionValida? '1 {} {})]
    (is (true? validez-expresion)))))

(deftest ejecutar-tipo-basico-numero
  (testing "Un numero debe ser ejecutable y devolver lo mismo"
    (let [
        resultado (ejecutarFuncion '1 {} {})]
    (is (= '1 resultado)))))
    
(deftest validez-tipo-basico-string
  (testing "Un string debe ser valido"
    (let [
        validez-expresion (expresionValida? '"hola" {} {})]
    (is (true? validez-expresion)))))
    
(deftest ejecutar-tipo-basico-string
  (testing "Un string debe ser ejecutable y devolver lo mismo"
    (let [
        resultado (ejecutarFuncion '"hola" {} {})]
    (is (= "hola" resultado)))))

(deftest validez-tipo-expresion-division-con-float
  (testing "Un float debe ser valido para division"
    (let [
        validez-expresion (expresionValida? '(/ 3 0.5) {} {})]
    (is (true? validez-expresion)))))

(deftest ejecucion-tipo-expresion-division-con-float
  (testing "(/ 3 0.5) = 6.0"
    (let [
        resultado (ejecutarFuncion '(/ 3 0.5) {} {})]
    (is (= 6.0 resultado)))))

(deftest validez-tipo-expresion-mayor-con-float
  (testing "Un float debe ser valido para mayor o igual"
    (let [
        validez-expresion (expresionValida? '(>= 3 2.9) {} {})]
    (is (true? validez-expresion)))))

(deftest ejecutar-tipo-expresion-mayor-con-float
  (testing "(>= 3 2.9) = true"
    (let [
        resultado (ejecutarFuncion '(>= 3 2.9) {} {})]
    (is (true? resultado)))))

(deftest validar-expresion-compleja-con-float
  (testing "Un float debe ser valido"
    (let [
        validez-expresion (expresionValida? '(/ (+ 3 0.5) (- 0.8 0.3)) {} {})]
    (is (true? validez-expresion)))))

(deftest ejecutar-expresion-compleja-con-float
  (testing "(/ (+ 3 0.5) (- 0.8 0.3)) = 7.0"
    (let [
        resultado (ejecutarFuncion '(/ (+ 3 0.5) (- 0.8 0.3)) {} {})]
    (is (= 7.0 resultado)))))
        
(deftest validar-division-por-cero
  (testing "La division por cero debe ser no valida"
    (let [
        validez-expresion (expresionValida? '(/ 3 0) {} {})]
    (is (false? validez-expresion)))))

(deftest validar-division-por-cero-expr-compleja
  (testing "La division por cero debe ser no valida"
    (let [
        validez-expresion (expresionValida? '(/ 3 (* 8 0)) {} {})]
    (is (false? validez-expresion)))))
    
(deftest validez-expresion-current-no-existe-campo
  (testing "La expresion current debe ser invalida por inexistencia del campo"
    (let [
        validez-expresion (expresionValida? '(current "spam") {"important" false} {})]
    (is (false? validez-expresion)))))

(deftest validez-expresion-current-no-existe-campo-dato-extenso
  (testing "La expresion current debe ser invalida por inexistencia del campo"
    (let [
        validez-expresion (expresionValida? '(current "spam") {"draft" true, "important" false} {})]
    (is (false? validez-expresion)))))

(deftest validez-expresion-current-campo-false
  (testing "La expresion current debe ser valida"
    (let [
        validez-expresion (expresionValida? '(current "spam") {"spam" false} {})]
    (is (true? validez-expresion)))))

(deftest validez-expresion-current-campo-no-bool
  (testing "La expresion current debe ser valida"
    (let [
        validez-expresion (expresionValida? '(current "spam") {"spam" "pepito"} {})]
    (is (true? validez-expresion)))))
