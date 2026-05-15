import { Component, Input } from '@angular/core';
import { TaxCalculationResponse } from '../../models/tax.models';

interface Bracket {
  lower: number;
  upper: number;
  rate: number;
}

const SARS_BRACKETS: Bracket[] = [
  { lower: 1,         upper: 237100,   rate: 18 },
  { lower: 237101,    upper: 370500,   rate: 26 },
  { lower: 370501,    upper: 512800,   rate: 31 },
  { lower: 512801,    upper: 673000,   rate: 36 },
  { lower: 673001,    upper: 857900,   rate: 39 },
  { lower: 857901,    upper: 1817000,  rate: 41 },
  { lower: 1817001,   upper: Infinity, rate: 45 },
];

@Component({
  selector: 'app-tax-results',
  templateUrl: './tax-results.component.html',
  styleUrls: ['./tax-results.component.scss']
})
export class TaxResultsComponent {

  @Input() result!: TaxCalculationResponse;

  get bracketLabel(): string {
    const income = this.result.grossAnnual;
    if (income <= 0) return 'Below tax threshold';
    const bracket = SARS_BRACKETS.find(b => income >= b.lower && income <= b.upper);
    if (!bracket) return 'Unknown';
    const upper = bracket.upper === Infinity
      ? 'and above'
      : `to R${bracket.upper.toLocaleString('en-ZA')}`;
    return `${bracket.rate}% marginal rate  (R${bracket.lower.toLocaleString('en-ZA')} ${upper})`;
  }
}
