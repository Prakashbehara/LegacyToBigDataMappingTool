import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataAsset } from 'app/shared/model/data-asset.model';

type EntityResponseType = HttpResponse<IDataAsset>;
type EntityArrayResponseType = HttpResponse<IDataAsset[]>;

@Injectable({ providedIn: 'root' })
export class DataAssetService {
    private resourceUrl = SERVER_API_URL + 'api/data-assets';

    constructor(private http: HttpClient) {}

    create(dataAsset: IDataAsset): Observable<EntityResponseType> {
        return this.http.post<IDataAsset>(this.resourceUrl, dataAsset, { observe: 'response' });
    }

    update(dataAsset: IDataAsset): Observable<EntityResponseType> {
        return this.http.put<IDataAsset>(this.resourceUrl, dataAsset, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDataAsset>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDataAsset[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
