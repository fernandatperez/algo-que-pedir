export type InputField = {
  label_for: string;
  label_text: string;
  input_placeholder: string;
  input_id: string;
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

export type FormData = {
  storeInfo: Record<string, string>;
  storeDir: Record<string, string>;
  storeCommission: Record<string, string>;
  paymentMethods: Record<string, boolean>;
};