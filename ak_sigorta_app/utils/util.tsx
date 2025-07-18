import AsyncStorage from '@react-native-async-storage/async-storage';
import { IUser } from '../models/IUser'
import CryptoJS from "react-native-crypto-js";


export const userStore = async (user: IUser) => {
  const stUser = JSON.stringify(user)
  await AsyncStorage.setItem('user', stUser )
}

export const userGet = async () => {
  const stUser = await AsyncStorage.getItem('user')
  if (stUser) {
    try {
      const user = JSON.parse(stUser) as IUser
    return user
    }catch(error) {
      return null
    }
  }
  return null
}

// key123
export const encrypt = (plainText:string) => {
  const ciphertext = CryptoJS.AES.encrypt(plainText, 'key123').toString();
  return ciphertext
}

export const decrypt = (cipherText:string) => {
  const bytes  = CryptoJS.AES.decrypt(cipherText, 'key123');
  const plainText = bytes.toString(CryptoJS.enc.Utf8);
  return plainText
}