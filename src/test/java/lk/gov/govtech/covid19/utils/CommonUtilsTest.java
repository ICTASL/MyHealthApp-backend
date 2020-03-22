package lk.gov.govtech.covid19.utils;

import org.junit.jupiter.api.Test;

import lk.gov.govtech.covid19.util.Constants;

import static org.assertj.core.api.Assertions.assertThat;

class CommonUtilsTest {

    @Test
    void testConstants() {
        
        assertThat(Constants.PUSH_NOTIFICATION_MESSAGE_TYPE_ALERT).isEqualTo("alert");
        assertThat(Constants.PUSH_NOTIFICATION_MESSAGE_TYPE_CASE).isEqualTo("case");
 
    }

}