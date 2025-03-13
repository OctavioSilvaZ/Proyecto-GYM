import { HttpClient } from '@angular/common/http';
import { Injectable, model } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { LoginInterface } from '../interface/login-interface';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private _htt: HttpClient) { }

  getToken(model: LoginInterface): Observable<any> {
    return this._htt.post(`${environment.api}auth/login`, model,
      { 'headers': { 'content-type': 'application/json' } });
  }

}
