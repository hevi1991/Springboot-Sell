package com.hevi;

import lombok.Data;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaTest {

    @Data
    private class Person {
        private String name;
        private List<Car> cars;

        public Person(String name, List<Car> cars) {
            this.name = name;
            this.cars = cars;
        }
    }

    @Data
    private class Car {
        private String type;
        private Integer maxSpeed;

        public Car(String type, Integer maxSpeed) {
            this.type = type;
            this.maxSpeed = maxSpeed;
        }
    }


    @Test
    public void lambdaTest() throws Exception {

        Car c1 = new Car("奥迪", 200);
        Car c2 = new Car("奔驰", 221);
        Car c3 = new Car("五菱", 120);
        Car c4 = new Car("GTR", 320);
        Car c5 = new Car("雪佛兰", 123);

        List<Car> cars1 = Arrays.asList(c2, c4, c5);
        List<Car> cars2 = Arrays.asList(c1, c5);
        List<Car> cars3 = Arrays.asList(c1, c2, c3, c4, c5);

        Person peter = new Person("Peter",cars1);
        Person anna = new Person("Anna",cars2);
        Person tom = new Person("Tom",cars3);

        List<Person> personList = Arrays.asList(peter, anna, tom);

        List nameList = personList.stream().map(p->p.getName()).collect(Collectors.toList());
        System.out.println(nameList);

        List pList = personList.stream().filter(p->p.getCars().size()>=3).collect(Collectors.toList());
        System.out.println(pList);

    }
}
