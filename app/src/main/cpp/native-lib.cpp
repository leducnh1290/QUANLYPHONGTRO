#include <jni.h>
#include <string>

extern "C" {
// create by duc anh
    JNIEXPORT jstring
    Java_com_leducanh_phongtro_DataBase_GetKey(JNIEnv *env, jobject object) {
        std::string api_key = "PhongTroGetPass@2004";
        return env->NewStringUTF(api_key.c_str());
    }
JNIEXPORT jstring
    Java_com_leducanh_phongtro_DataBase_GetKeyData(JNIEnv *env, jobject object) {
        std::string key_data = "Data";
        return env->NewStringUTF(key_data.c_str());
    }
}