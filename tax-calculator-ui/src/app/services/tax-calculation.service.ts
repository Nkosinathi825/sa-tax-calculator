import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TaxCalculationRequest, TaxCalculationResponse } from '../models/tax.models';

@Injectable({ providedIn: 'root' })
export class TaxCalculationService {

  private readonly apiUrl = '/api/calculate';

  constructor(private http: HttpClient) {}

  calculate(request: TaxCalculationRequest): Observable<TaxCalculationResponse> {
    return this.http.post<TaxCalculationResponse>(this.apiUrl, request);
  }
}
