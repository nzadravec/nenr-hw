package hr.fer.zemris.fuzzy.set;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.domain.IDomain;
import hr.fer.zemris.fuzzy.domain.SimpleDomain;

public class FuzzySets {

	public static IFuzzySet far;
	public static IFuzzySet close;
	public static IFuzzySet veryClose;
	public static IFuzzySet identityDistance;
	public static IFuzzySet fast;
	public static IFuzzySet slow;
	public static IFuzzySet identityVelocity;
	public static IFuzzySet accelerate;
    public static IFuzzySet decelerate;
    public static IFuzzySet stayTheSame;
    public static IFuzzySet hardRight;
    public static IFuzzySet softRight;
    public static IFuzzySet hardLeft;
    public static IFuzzySet softLeft;
    public static IFuzzySet wrongWay;
    public static IFuzzySet rightWay;
    
    static {
    	
    	IDomain distanceDomain = new SimpleDomain(0, 1301);
    	identityDistance = new CalculatedFuzzySet(distanceDomain, x -> 1);
    	
    	IDomain velocityDomain = new SimpleDomain(0, 51);
    	identityVelocity = new CalculatedFuzzySet(velocityDomain, x -> 1);
    	
    	IDomain accelerationDomain = new SimpleDomain(-5, 6);
    	IDomain rudderDomain = new SimpleDomain(-90, 91);
    	
    	IDomain directionDomain = new SimpleDomain(0, 2);
    	wrongWay = new MutableFuzzySet(directionDomain).set(DomainElement.of(0), 1);
    	rightWay = new MutableFuzzySet(directionDomain).set(DomainElement.of(1), 1);
    	
    	close = new CalculatedFuzzySet(
    			distanceDomain,
                StandardFuzzySets.lFunction(
                		distanceDomain.indexOfElement(DomainElement.of(10)),
                		distanceDomain.indexOfElement(DomainElement.of(50))
                )
        );
    	
    	far = new CalculatedFuzzySet(
    			distanceDomain,
                StandardFuzzySets.gammaFunction(
                		distanceDomain.indexOfElement(DomainElement.of(60)),
                		distanceDomain.indexOfElement(DomainElement.of(100))
                )
        );
    	
    	fast = new CalculatedFuzzySet(
    			velocityDomain,
                StandardFuzzySets.gammaFunction(
                		velocityDomain.indexOfElement(DomainElement.of(30)),
                		velocityDomain.indexOfElement(DomainElement.of(35))
                )
        );
    	
        slow = new CalculatedFuzzySet(
        		velocityDomain,
                StandardFuzzySets.lFunction(
                		velocityDomain.indexOfElement(DomainElement.of(30)),
                		velocityDomain.indexOfElement(DomainElement.of(35))
                )
        );
    	
//    	fast = new CalculatedFuzzySet(
//    			velocityDomain,
//                StandardFuzzySets.gammaFunction(
//                		velocityDomain.indexOfElement(DomainElement.of(70)),
//                		velocityDomain.indexOfElement(DomainElement.of(100))
//                )
//        );
//        slow = new CalculatedFuzzySet(
//        		velocityDomain,
//                StandardFuzzySets.lFunction(
//                		velocityDomain.indexOfElement(DomainElement.of(35)),
//                		velocityDomain.indexOfElement(DomainElement.of(75))
//                )
//        );
        
        accelerate = new MutableFuzzySet(accelerationDomain).set(DomainElement.of(2), 1);
        decelerate = new MutableFuzzySet(accelerationDomain).set(DomainElement.of(-2), 1);
       
//        accelerate = new CalculatedFuzzySet(
//        		accelerationDomain,
//                StandardFuzzySets.gammaFunction(
//                		accelerationDomain.indexOfElement(DomainElement.of(6)),
//                		accelerationDomain.indexOfElement(DomainElement.of(9))
//                )
//        );
//        decelerate = new CalculatedFuzzySet(
//        		accelerationDomain,
//                StandardFuzzySets.lFunction(
//                		accelerationDomain.indexOfElement(DomainElement.of(-9)),
//                		accelerationDomain.indexOfElement(DomainElement.of(-4))
//                )
//        );
        
//        decelerate = new CalculatedFuzzySet(
//                accelerationDomain,
//                StandardFuzzySets.lFunction(
//                        accelerationDomain.indexOfElement(DomainElement.of(-4)),
//                        accelerationDomain.indexOfElement(DomainElement.of(-2))
//                )
//        );
        
        close = new CalculatedFuzzySet(
        		distanceDomain,
                StandardFuzzySets.lFunction(
                		distanceDomain.indexOfElement(DomainElement.of(60)),
                		distanceDomain.indexOfElement(DomainElement.of(80))
                )
        );
        
        veryClose = new CalculatedFuzzySet(
        		distanceDomain,
                StandardFuzzySets.lFunction(
                		distanceDomain.indexOfElement(DomainElement.of(40)),
                		distanceDomain.indexOfElement(DomainElement.of(60))
                )
        );
        
        far = new CalculatedFuzzySet(
        		distanceDomain,
                StandardFuzzySets.gammaFunction(
                		distanceDomain.indexOfElement(DomainElement.of(50)),
                		distanceDomain.indexOfElement(DomainElement.of(80))
                )
        );
        
        hardLeft = new CalculatedFuzzySet(
                rudderDomain,
                StandardFuzzySets.gammaFunction(
                		//rudderDomain.indexOfElement(DomainElement.of(80)),
                        rudderDomain.indexOfElement(DomainElement.of(85)),
                        rudderDomain.indexOfElement(DomainElement.of(90))
                )
        );
        hardRight = new CalculatedFuzzySet(
                rudderDomain,
                StandardFuzzySets.lFunction(
                        rudderDomain.indexOfElement(DomainElement.of(-90)),
                        rudderDomain.indexOfElement(DomainElement.of(-85))
                        //rudderDomain.indexOfElement(DomainElement.of(-80))
                )
        );
        softLeft = new CalculatedFuzzySet(
                rudderDomain,
                StandardFuzzySets.lambdaFunction(
                        rudderDomain.indexOfElement(DomainElement.of(20)),
                        rudderDomain.indexOfElement(DomainElement.of(35)),
                        rudderDomain.indexOfElement(DomainElement.of(50))
                )
        );
        softRight = new CalculatedFuzzySet(
                rudderDomain,
                StandardFuzzySets.lambdaFunction(
                        rudderDomain.indexOfElement(DomainElement.of(-50)),
                        rudderDomain.indexOfElement(DomainElement.of(-35)),
                        rudderDomain.indexOfElement(DomainElement.of(-20))
                )
        );
    	
    }
	
}
