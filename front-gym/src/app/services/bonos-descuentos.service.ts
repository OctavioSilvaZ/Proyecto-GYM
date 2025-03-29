import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { BonoRequest } from '../interface/bono-request';
import { BonoIndividualRequest } from '../interface/bono-individual-request';

@Injectable({
  providedIn: 'root'
})
export class BonosYdescuentosService {

  constructor(private _http: HttpClient) { }

  getBonos(token: any): Observable<any> {
    return this._http.get(`${environment.api}v1/admin/bonos`,
      { 'headers': { 'Authorization': `Bearer ${token}` } }
    );
  }

  registraBono(modelo: BonoRequest, token: any): Observable<any> {
    return this._http.post(`${environment.api}v1/admin/bonos/registrar`, modelo,
      { 'headers': { 'Authorization': `Bearer ${token}` } });
  }

  bonoEmpleados(idBono: number, token: any): Observable<any> {
    return this._http.put(`${environment.api}v1/admin/bonos/empleados/${idBono}`, {},
      { 'headers': { 'Authorization': `Bearer ${token}` } }
    );
  }

  bonoIndividual(request: BonoIndividualRequest, token: any): Observable<any> {
    return this._http.put(`${environment.api}v1/admin/bonos/individual`, request,
      { 'headers': { 'Authorization': `Bearer ${token}` } }
    )
  }


}
