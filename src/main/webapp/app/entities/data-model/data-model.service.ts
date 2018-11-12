import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataModel } from 'app/shared/model/data-model.model';

type EntityResponseType = HttpResponse<IDataModel>;
type EntityArrayResponseType = HttpResponse<IDataModel[]>;

@Injectable({ providedIn: 'root' })
export class DataModelService {
    private resourceUrl = SERVER_API_URL + 'api/data-models';

    constructor(private http: HttpClient) {}

    create(dataModel: IDataModel): Observable<EntityResponseType> {
        return this.http.post<IDataModel>(this.resourceUrl, dataModel, { observe: 'response' });
    }

    update(dataModel: IDataModel): Observable<EntityResponseType> {
        return this.http.put<IDataModel>(this.resourceUrl, dataModel, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDataModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDataModel[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
