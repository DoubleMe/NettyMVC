package demo;

import org.springframework.beans.factory.annotation.Autowired;

import com.chm.SpringContext.SpringContext;
import com.chm.parameter.ParameterManager;
import com.chm.parameter.impl.ParameterManagerImpl;

import junit.framework.TestCase;

public class ParameterTest extends TestCase{
	
	private ParameterManager parameterManager = new ParameterManagerImpl();;
	
	public ParameterTest() {
		SpringContext.initSpringContext();
	}

	
	public void testParameterTest(){
		//parameterManager.buildArgs(action);
	}
}
