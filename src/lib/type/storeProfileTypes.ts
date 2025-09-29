export type InputField = {
  label_for: string;
  label_text: string;
  input_placeholder: string;
  input_id: string;
  required?: boolean; 
  type?:  'number' | 'text' | 'url';
  minLength?: number;
  maxLength?: number;
};

export type FieldsetGroupProps = {
  title: string;
  name: string;
  fields: InputField[];
};

export type PaymentMethod = {
  id: string;
  label: string;
  value: string;
  checked?: boolean;
};

// dejo todo bien tipado para no tener problemas
export interface StoreInfo {
  'store-name': string;
  'url-store-img'?: string;
  [key: string]: string | undefined; 
}

export interface StoreDir {
  'store-dir': string;                   
  'store-altitude'?: number;              
  'store-latitude'?: number;               
  'store-longitude'?: number;             
  [key: string]: number | string | undefined; 
}

export interface StoreCommission {
  'app-commission': string;
  'author-commission': string;
  [key: string]: string | undefined;
}

export interface PaymentMethods {
  'efectivo': boolean;
  'qr': boolean;
  'transferencia': boolean;
  [key: string]: boolean;
}

export interface FormData {
  storeInfo: StoreInfo;
  storeDir: StoreDir;
  storeCommission: StoreCommission;
  paymentMethods: PaymentMethods;
}

// defino la key asi por que sino me dice que no esta tipada correctamente
export type FormSection = keyof FormData;