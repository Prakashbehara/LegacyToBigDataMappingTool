import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SourceMapping } from 'app/shared/model/source-mapping.model';
import { SourceMappingService } from './source-mapping.service';
import { SourceMappingComponent } from './source-mapping.component';
import { SourceMappingDetailComponent } from './source-mapping-detail.component';
import { SourceMappingUpdateComponent } from './source-mapping-update.component';
import { SourceMappingDeletePopupComponent } from './source-mapping-delete-dialog.component';
import { ISourceMapping } from 'app/shared/model/source-mapping.model';

@Injectable({ providedIn: 'root' })
export class SourceMappingResolve implements Resolve<ISourceMapping> {
    constructor(private service: SourceMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sourceMapping: HttpResponse<SourceMapping>) => sourceMapping.body));
        }
        return of(new SourceMapping());
    }
}

export const sourceMappingRoute: Routes = [
    {
        path: 'source-mapping',
        component: SourceMappingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-mapping/:id/view',
        component: SourceMappingDetailComponent,
        resolve: {
            sourceMapping: SourceMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-mapping/new',
        component: SourceMappingUpdateComponent,
        resolve: {
            sourceMapping: SourceMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-mapping/:id/edit',
        component: SourceMappingUpdateComponent,
        resolve: {
            sourceMapping: SourceMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sourceMappingPopupRoute: Routes = [
    {
        path: 'source-mapping/:id/delete',
        component: SourceMappingDeletePopupComponent,
        resolve: {
            sourceMapping: SourceMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
