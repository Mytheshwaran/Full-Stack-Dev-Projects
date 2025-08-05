echo "Enter the number1:"
read num1
echo "Enter the number2:"
read num2
echo "Enter the operation:"
echo "1.Addition, 2.Subtration, 3.Multiplication, 4.Division"
read opr
case $opr in
1)
	let add=num1+num2
	echo "Addition of $num1 & $num2: $add";;
2)
	let sub=num1-num2
	echo "Subtraction of $num1 & $num2: $sub";;
3)
        let mul=num1*num2
        echo "Multiplication of $num1 & $num2: $mul";;
4)
        let div=num1/num2
        echo "Division of $num1 & $num2: $div";;
*)
	echo "Sorry";;
esac

