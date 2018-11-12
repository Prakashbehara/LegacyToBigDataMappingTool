import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SourceDatabaseMapping } from 'app/shared/model/source-database-mapping.model';
import { SourceDatabaseMappingService } from './source-database-mapping.service';
import { SourceDatabaseMappingComponent } from './source-database-mapping.component';
import { SourceDatabaseMappingDetailComponent } from './source-database-mapping-detail.component';
import { SourceDatabaseMappingUpdateComponent } from './source-database-mapping-update.component';
import { SourceDatabaseMappingDeletePopupComponent } from './source-database-mapping-delete-dialog.component';
import { ISourceDatabaseMapping } from 'app/shared/model/source-database-mapping.model';

@Injectable({ providedIn: 'root' })
export class SourceDatabaseMappingResolve implements Resolve<ISourceDatabaseMapping> {
    constructor(private service: SourceDatabaseMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((sourceDatabaseMapping: HttpResponse<SourceDatabaseMapping>) => sourceDatabaseMapping.body));
        }
        return of(new SourceDatabaseMapping());
    }
}

export const sourceDatabaseMappingRoute: Routes = [
    {
        path: 'source-database-mapping',
        component: SourceDatabaseMappingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDatabaseMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-database-mapping/:id/view',
        component: SourceDatabaseMappingDetailComponent,
        resolve: {
            sourceDatabaseMapping: SourceDatabaseMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDatabaseMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-database-mapping/new',
        component: SourceDatabaseMappingUpdateComponent,
        resolve: {
            sourceDatabaseMapping: SourceDatabaseMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDatabaseMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-database-mapping/:id/edit',
        component: SourceDatabaseMappingUpdateComponent,
        resolve: {
            sourceDatabaseMapping: SourceDatabaseMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDatabaseMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sourceDatabaseMappingPopupRoute: Routes = [
    {
        path: 'source-database-mapping/:id/delete',
        component: SourceDatabaseMappingDeletePopupComponent,
        resolve: {
            sourceDatabaseMapping: SourceDatabaseMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDatabaseMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
