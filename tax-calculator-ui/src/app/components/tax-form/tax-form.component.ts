import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TaxCalculationService } from '../../services/tax-calculation.service';
import { TaxCalculationResponse } from '../../models/tax.models';

@Component({
  selector: 'app-tax-form',
  templateUrl: './tax-form.component.html',
  styleUrls: ['./tax-form.component.scss']
})
export class TaxFormComponent {

  @Output() calculated = new EventEmitter<TaxCalculationResponse>();

  form: FormGroup;
  loading = false;
  error: string | null = null;

  constructor(private fb: FormBuilder, private taxService: TaxCalculationService) {
    this.form = this.fb.group({
      grossAnnual: [null, [Validators.required, Validators.min(0)]],
      age: [null, [Validators.required, Validators.min(0), Validators.max(130)]],
      medicalAidMembers: [0, [Validators.min(0), Validators.max(20)]]
    });
  }

  onSubmit(): void {
    Object.values(this.form.controls).forEach(c => c.markAsTouched());
    if (this.form.invalid) return;
    this.loading = true;
    this.error = null;
    this.taxService.calculate(this.form.value).subscribe({
      next: result => {
        this.loading = false;
        this.error = null;
        this.calculated.emit(result);
      },
      error: err => {
        this.loading = false;
        this.error = err.status === 400
          ? 'Invalid input — please check your values and try again.'
          : 'Could not reach the tax calculator. Please ensure the backend is running on port 8080.';
      }
    });
  }
}
