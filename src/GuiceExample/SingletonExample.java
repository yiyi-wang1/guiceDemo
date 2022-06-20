package GuiceExample;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;

interface Car{
    int drive();
}

@Singleton
final class BMW implements Car {
    @Inject
    BMW(){}

    @Override
    public int drive(){
        return 60;
    }

    public static BMW create(){
        return new BMW();
    }
}

final class Owner{
    private final Car car;

    @Inject
    Owner(Car car){
        this.car = car;
    }

    public Car getCar(){
        return this.car;
    }
}

final class CarModule extends AbstractModule{
    @Override
    protected void configure() {
        //Method 1
        //bind(the interface/superclass).to(implementation/subclass)
        bind(Car.class).to(BMW.class);

        //Method 2
        //bind the class to Instance (singleton)
        bind(Car.class).toInstance(BMW.create());
    }

    //Method 3
    //Use Provides Annotation
    @Provides
    Car provideCar(){
        return BMW.create();
    }
}

public class SingletonExample {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CarModule());
        Owner owner = injector.getInstance(Owner.class);
        System.out.println("With Guice car example: This car can drive at speed: " + owner.getCar().drive());
    }
}
