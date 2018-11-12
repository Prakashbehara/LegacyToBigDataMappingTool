/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SourceDatabaseUpdateComponent } from 'app/entities/source-database/source-database-update.component';
import { SourceDatabaseService } from 'app/entities/source-database/source-database.service';
import { SourceDatabase } from 'app/shared/model/source-database.model';

describe('Component Tests', () => {
    describe('SourceDatabase Management Update Component', () => {
        let comp: SourceDatabaseUpdateComponent;
        let fixture: ComponentFixture<SourceDatabaseUpdateComponent>;
        let service: SourceDatabaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceDatabaseUpdateComponent]
            })
                .overrideTemplate(SourceDatabaseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SourceDatabaseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceDatabaseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SourceDatabase(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sourceDatabase = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SourceDatabase();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sourceDatabase = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
