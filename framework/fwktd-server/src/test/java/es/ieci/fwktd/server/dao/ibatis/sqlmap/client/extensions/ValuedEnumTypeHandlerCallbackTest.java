package es.ieci.fwktd.server.dao.ibatis.sqlmap.client.extensions;

import java.sql.Types;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.ValuedEnumTypeHandlerCallback;

@RunWith(JUnit4.class)
public class ValuedEnumTypeHandlerCallbackTest {

	@Test
	public void readEnumAsOrdinal() {
		ValuedEnumTypeHandlerCallback<MockValuedEnum> callback = new ValuedEnumTypeHandlerCallback<MockValuedEnum>(
				MockValuedEnum.class, true);

		ResultGetter getter = EasyMock.createMock(ResultGetter.class);
		try {
			EasyMock.expect(getter.getInt()).andReturn(Integer.valueOf(1));
			EasyMock.expect(getter.wasNull()).andReturn(Boolean.FALSE);
			EasyMock.replay(getter);

			Object result = callback.getResult(getter);

			Assert.assertNotNull(result);
			Assert.assertTrue(result instanceof MockValuedEnum);
			Assert.assertEquals(1, ((MockValuedEnum) result).getValue());
		} catch (Exception e) {
			Assert.fail();
		} finally {
			EasyMock.verify(getter);
		}
	}

	@Test
	public void readEnumAsName() {
		ValuedEnumTypeHandlerCallback<MockValuedEnum> callback = new ValuedEnumTypeHandlerCallback<MockValuedEnum>(
				MockValuedEnum.class, false);

		ResultGetter getter = EasyMock.createMock(ResultGetter.class);
		try {
			EasyMock.expect(getter.getString()).andReturn("ENUM_1");
			EasyMock.expect(getter.wasNull()).andReturn(Boolean.FALSE);
			EasyMock.replay(getter);

			Object result = callback.getResult(getter);

			Assert.assertNotNull(result);
			Assert.assertTrue(result instanceof MockValuedEnum);
			Assert.assertEquals("ENUM_1", ((MockValuedEnum) result).getName());
		} catch (Exception e) {
			Assert.fail();
		} finally {
			EasyMock.verify(getter);
		}
	}

	@Test
	public void writeEnumAsOrdinal() {
		ValuedEnumTypeHandlerCallback<MockValuedEnum> callback = new ValuedEnumTypeHandlerCallback<MockValuedEnum>(
				MockValuedEnum.class, true);

		ParameterSetter setter = EasyMock.createMock(ParameterSetter.class);
		try {
			setter.setInt(MockValuedEnum.MOCK_ENUM_1.getValue());
			EasyMock.expectLastCall();
			EasyMock.replay(setter);

			callback.setParameter(setter, MockValuedEnum.MOCK_ENUM_1);
		} catch (Exception e) {
			Assert.fail();
		} finally {
			EasyMock.verify(setter);
		}
	}

	@Test
	public void writeEnumAsName() {
		ValuedEnumTypeHandlerCallback<MockValuedEnum> callback = new ValuedEnumTypeHandlerCallback<MockValuedEnum>(
				MockValuedEnum.class, false);

		ParameterSetter setter = EasyMock.createMock(ParameterSetter.class);
		try {
			setter.setString(MockValuedEnum.MOCK_ENUM_1.getName());
			EasyMock.expectLastCall();
			EasyMock.replay(setter);

			callback.setParameter(setter, MockValuedEnum.MOCK_ENUM_1);
		} catch (Exception e) {
			Assert.fail();
		} finally {
			EasyMock.verify(setter);
		}
	}

	@Test
	public void writeNullEnumAsOrdinal() {
		ValuedEnumTypeHandlerCallback<MockValuedEnum> callback = new ValuedEnumTypeHandlerCallback<MockValuedEnum>(
				MockValuedEnum.class, true);

		ParameterSetter setter = EasyMock.createMock(ParameterSetter.class);
		try {
			setter.setNull(Types.INTEGER);
			EasyMock.expectLastCall();
			EasyMock.replay(setter);

			callback.setParameter(setter, null);
		} catch (Exception e) {
			Assert.fail();
		} finally {
			EasyMock.verify(setter);
		}
	}

	@Test
	public void writeNullEnumAsName() {
		ValuedEnumTypeHandlerCallback<MockValuedEnum> callback = new ValuedEnumTypeHandlerCallback<MockValuedEnum>(
				MockValuedEnum.class, false);

		ParameterSetter setter = EasyMock.createMock(ParameterSetter.class);
		try {
			setter.setNull(Types.VARCHAR);
			EasyMock.expectLastCall();
			EasyMock.replay(setter);

			callback.setParameter(setter, null);
		} catch (Exception e) {
			Assert.fail();
		} finally {
			EasyMock.verify(setter);
		}
	}

	@Test
	public void readNullEnumAsOrdinal() {
		ValuedEnumTypeHandlerCallback<MockValuedEnum> callback = new ValuedEnumTypeHandlerCallback<MockValuedEnum>(
				MockValuedEnum.class, true);

		ResultGetter getter = EasyMock.createNiceMock(ResultGetter.class);
		try {
			EasyMock.expect(getter.wasNull()).andReturn(true);
			EasyMock.replay(getter);

			Object result = callback.getResult(getter);

			Assert.assertNull(result);
		} catch (Exception e) {
			Assert.fail();
		} finally {
			EasyMock.verify(getter);
		}
	}

	@Test
	public void readNullEnumAsName() {
		ValuedEnumTypeHandlerCallback<MockValuedEnum> callback = new ValuedEnumTypeHandlerCallback<MockValuedEnum>(
				MockValuedEnum.class, false);

		ResultGetter getter = EasyMock.createMock(ResultGetter.class);
		try {
			EasyMock.expect(getter.getString()).andReturn(null);
			EasyMock.expect(getter.wasNull()).andReturn(true);
			EasyMock.replay(getter);

			Object result = callback.getResult(getter);

			Assert.assertNull(result);
		} catch (Exception e) {
			Assert.fail();
		} finally {
			EasyMock.verify(getter);
		}
	}
}
