echo "Enter the file name:"
read fname
file=$(find ./ -name $fname)
#file="./file.properties"
if [ -z  "$file" ]
then 
	echo "File not exist"
	echo "math.sh program is running"
        source math.sh
else
	echo "File copied in newval.txt"
  	grep "$1" $file | cut -d'=' -f2 > newval.txt
fi

















##Dictionary
	#source property.txt	
	#	key=name
	#	value=mythesh
	#pairs[$key]=$value
	#echo "Need to edit value (y or n):"
	#read opt
	#if [ "$opt" == y ]
	#then 
	#	echo "Enter the new value:"
	#	read newval
	#	pairs[key]=$newval
	#	echo "Updated in newval.txt"
	#	echo "${pairs[key]}"> newval.txt
	#else
	#	echo "Copied in newval.txt"	
	#	echo "${pairs[key]}"> newval.txt
	#fi
