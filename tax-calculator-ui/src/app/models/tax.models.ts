export interface TaxCalculationRequest {
  grossAnnual: number;
  age: number;
  medicalAidMembers: number;
}

export interface TaxCalculationResponse {
  grossAnnual: number;
  taxableIncome: number;
  annualTax: number;
  monthlyPaye: number;
  uifMonthly: number;
  medicalAidCreditMonthly: number;
  medicalAidCreditAnnual: number;
  netMonthly: number;
  effectiveTaxRate: number;
}
