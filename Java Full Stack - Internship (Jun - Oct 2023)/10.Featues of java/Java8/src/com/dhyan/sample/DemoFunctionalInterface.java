package com.dhyan.sample;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

@FunctionalInterface
interface DemoInterface{
	static void staticMethod()
	{
		System.out.println("Static Method");
	}
	default void displayString()
	{
		System.out.println("Default Method");
	}
	void display();
}

class ImplementationInterface implements DemoInterface{
	public void display()
	{
		System.out.println("Abstract method of functional interface");
	}
}

/**
 * Consumer Interface
 * 
 */
class ConsumerInterface implements Consumer<ConsumerInterface>
{
	private String name;
	
	public ConsumerInterface(String name)
	{
		this.name=name;
	}
	
	public static void displayListAccept(List<Integer> list)
	{
		System.out.println("Consumer accept");
		list.stream().forEach(x->System.out.println(x));
	}
	public static void displayListAndThen(List<Integer> list)
	{
		System.out.println("Consumer AndThen");
		list.stream().forEach(x->System.out.println(x+1));
	}
	
	//Bi-consumer interface
	public static void displayBiconsumerAccept(List<ConsumerInterface> list,String name)
	{
		System.out.println("--BICONSUMER ACCEPT() "+name);
		list.stream().forEach(x->System.out.println(x));
	}
	public static void displayBiconsumerAndThen(List<ConsumerInterface> list,String name)
	{
		System.out.println("--BICONSUMER ANDTHEN() "+name);
		list.parallelStream().map(x->x+"newtext").forEach(x->System.out.println(x));
	}
	
	@Override
	public void accept(ConsumerInterface t)
	{
		System.out.println(t);
	}
	@Override
	public String toString()
	{
		return name;
	}
}
class PredicateInterface
{
	public static boolean predicateTest(int age)
	{
		if(age>17)
			return true;
		else
			return false;
	}
	
}
public class DemoFunctionalInterface
{
	public static void main(String[] args) {
		//FunctionalInterface
		ImplementationInterface obj=new ImplementationInterface();
		obj.display();
		obj.displayString();
		
		DemoInterface.staticMethod();
		
		//Consumer Interface(accept & andThen)
		List<Integer> list=Arrays.asList(10,20,30,40,50);
		Consumer<List<Integer>> displayList=ConsumerInterface::displayListAccept;
		displayList.accept(list);
		System.out.println("----------");
		Consumer<List<Integer>> displayStringList=ConsumerInterface::displayListAndThen;
		displayList.andThen(displayStringList).accept(list);;
		
		System.out.println("---Override using consumer class interface---");
		//ConsumerInterface class Override consumer class method (accept()) so it is possible to use consumer method directly
		ConsumerInterface consumerobj=new ConsumerInterface("abc");
		consumerobj.accept(consumerobj);
		
		System.out.println("~~~~~************~~~~");
		//BiConsumer Interface(accept)
		List<ConsumerInterface> objList=new ArrayList<ConsumerInterface>();
		objList.add(new ConsumerInterface("def"));
		objList.add(new ConsumerInterface("ghi"));
		objList.add(new ConsumerInterface("jkl"));
		BiConsumer<List<ConsumerInterface>,String> newBiList1=ConsumerInterface::displayBiconsumerAccept;
		newBiList1.accept(objList,"String");
		System.out.println("----------");
		BiConsumer<List<ConsumerInterface>,String> newBiList2=ConsumerInterface::displayBiconsumerAndThen;
		newBiList1.andThen(newBiList2).accept(objList, "String");
		
		System.out.println("~~~~~************~~~~");
		//Predicate Interface(test)
		Predicate<Integer> predicate=PredicateInterface::predicateTest;
		System.out.println("Age is greater than 17 "+predicate.test(18));
	}
}
